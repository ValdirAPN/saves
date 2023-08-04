package br.com.saves.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class NumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        val out = if (original.isEmpty()) {
            original
        } else {
            (original.toDouble() / 100).toNumberFormat()
        }
        return TransformedText(
            AnnotatedString(out),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = out.length
                override fun transformedToOriginal(offset: Int): Int = original.length
            }
        )
    }
}
