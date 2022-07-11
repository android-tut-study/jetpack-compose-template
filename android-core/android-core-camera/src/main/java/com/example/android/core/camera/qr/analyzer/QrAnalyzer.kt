package com.example.ui.qr.analyzer

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

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
            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
                        )
                    )
                }.decode(binaryBitmap)
                onScanned(result.text)
            } catch (e: Exception) {
            } finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}