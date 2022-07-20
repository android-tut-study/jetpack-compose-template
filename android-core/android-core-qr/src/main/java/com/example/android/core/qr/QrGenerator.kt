package com.example.android.core.qr

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel


const val DEFAULT_SCALE_FACTOR = 3

@Throws(RuntimeException::class)
fun generateQrBitmap(
    content: String,
    size: Int,
    valueColor: Int = Color.BLACK,
    backgroundColor: Int = Color.WHITE
): Bitmap {
    val hints = HashMap<EncodeHintType?, Any?>()
    // The Error Correction level H provide a QR Code that can be covered by 30%
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H

    val writer = QRCodeWriter()
    val matrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val width = matrix.width
    val height = matrix.height

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (matrix.get(x, y)) valueColor else backgroundColor)
        }
    }
    return bitmap
}

@Throws(RuntimeException::class)
fun generateQrLogoBitmap(
    content: String,
    size: Int,
    logo: Bitmap,
    valueColor: Int = Color.BLACK,
    backgroundColor: Int = Color.WHITE,
    scaleFactor: Int = DEFAULT_SCALE_FACTOR,
): Bitmap {
    val qrCodeBitmap = generateQrBitmap(content, size, valueColor, backgroundColor)
    return mergeBitmap(logo, qrCodeBitmap, scaleFactor)
}

fun mergeBitmap(overlay: Bitmap, bitmap: Bitmap, scaleFactor: Int = DEFAULT_SCALE_FACTOR): Bitmap {
    val width: Int = bitmap.width
    val height: Int = bitmap.height

    val combined = Bitmap.createBitmap(width, height, bitmap.config)
    val canvas = Canvas(combined)

    val canvasWidth = canvas.width
    val canvasHeight = canvas.height

    canvas.drawBitmap(bitmap, Matrix(), null)

    overlay.density = overlay.density * scaleFactor

    val centerX = (canvasWidth - overlay.width / scaleFactor) / 2
    val centerY = (canvasHeight - overlay.height / scaleFactor) / 2

    canvas.drawBitmap(overlay, centerX.toFloat(), centerY.toFloat(), null)
    return combined
}