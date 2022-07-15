package com.example.android.core.camera.qr.analyzer

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.GlobalHistogramBinarizer
import java.nio.ByteBuffer
import kotlin.reflect.KClass

@SuppressLint("InlinedApi")
val IMAGE_SUPPORTS = listOf(
    ImageFormat.YUV_420_888,
    ImageFormat.YUV_422_888,
    ImageFormat.YUV_444_888,
)

class QrAnalyzer(
    private val imageSupports: List<Int> = IMAGE_SUPPORTS,
    private val onScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        runCatching {
            if (image.format in imageSupports) {
                val bytes = image.planes.first().buffer.toByteArray()
                val source = PlanarYUVLuminanceSource(
                    bytes,
                    image.width,
                    image.height,
                    0,
                    0,
                    image.width,
                    image.height,
                    false
                )

                val binaryBitmap = BinaryBitmap(GlobalHistogramBinarizer(source))
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
                        )
                    )
                }.decode(binaryBitmap)
                onScanned(result.text)
            }
        }.catching(NotFoundException::class, IllegalArgumentException::class) { e ->
            if (e !is NotFoundException) {
                Log.w(this::class.simpleName, ">>> QrAnalyzer error due to $e <<<")
            }
        }.also {
            image.close()
        }.getOrThrow()
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}

inline fun <R> Result<R>.catching(
    vararg exceptionClasses: KClass<out Throwable>,
    transform: (exception: Throwable) -> R
) = recoverCatching { exception ->
    if (exceptionClasses.any { it.isInstance(exception) }) {
        transform(exception)
    } else throw exception
}
