package com.portal.weatherapp.utilities.customviews

import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.portal.weatherapp.R
import com.portal.weatherapp.databinding.WeatherCustomTextViewBinding
import com.portal.weatherapp.utilities.extensions.setColoredSpan
import com.portal.weatherapp.utilities.extensions.withClickableSpan

class WeatherCustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var _binding: WeatherCustomTextViewBinding? = null
    private val binding: WeatherCustomTextViewBinding
        get() = _binding!!

    private val attributes = context.obtainStyledAttributes(attrs, R.styleable.WeatherCustomTextView)
    private val _textSize = attributes.getDimension(
        R.styleable.WeatherCustomTextView_android_textSize,
        12F
    )
    private val textColor = attributes.getColor(
        R.styleable.WeatherCustomTextView_android_textColor,
        ContextCompat.getColor(context, R.color.white)
    )
    private val _maxLines = attributes.getInt(R.styleable.WeatherCustomTextView_android_maxLines, -1)
    private val _ellipsize = attributes.getInt(R.styleable.WeatherCustomTextView_android_ellipsize, 0)

    init {
        _binding = WeatherCustomTextViewBinding.inflate(LayoutInflater.from(context), this, true)
        initAttributes()
    }

    private fun initAttributes() {
        when (attributes.getInteger(R.styleable.WeatherCustomTextView_textType, 0)) {
            REGULAR -> binding.textView.typeface =
                ResourcesCompat.getFont(context, R.font.montserrat_regular)

            BOLD -> binding.textView.typeface =
                ResourcesCompat.getFont(context, R.font.montserrat_bold)

            MEDIUM -> binding.textView.typeface =
                ResourcesCompat.getFont(context, R.font.montserrat_medium)

            SEMIBOLD -> {
                binding.textView.typeface =
                    ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)
            }

            REGULAR_UNDERLINED -> {
                binding.textView.typeface =
                    ResourcesCompat.getFont(context, R.font.montserrat_regular)
                binding.textView.paint.isUnderlineText = true
            }

            MEDIUM_UNDERLINED -> {
                binding.textView.typeface =
                    ResourcesCompat.getFont(context, R.font.montserrat_medium)
                binding.textView.paint.isUnderlineText = true
            }

            BOLD_UNDERLINED -> {
                binding.textView.typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
                binding.textView.paint.isUnderlineText = true
            }

            SEMIBOLD_UNDERLINED -> {
                binding.textView.typeface =
                    ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)
                binding.textView.paint.isUnderlineText = true
            }
        }

        binding.textView.text = attributes.getString(R.styleable.WeatherCustomTextView_android_text)
        binding.textView.setTextColor(textColor)
        setMaxLines()
        setEllipsize()

        if (_textSize == 12f)
            binding.textView.textSize = _textSize
        else
            binding.textView.textSize = _textSize / resources.displayMetrics.scaledDensity

        when (attributes.getInteger(R.styleable.WeatherCustomTextView_textAlignment, 0)) {
            LEFT -> binding.textView.textAlignment = TEXT_ALIGNMENT_TEXT_START
            RIGHT -> binding.textView.textAlignment = TEXT_ALIGNMENT_TEXT_END
            CENTER -> binding.textView.textAlignment = TEXT_ALIGNMENT_CENTER
            JUSTIFY -> binding.textView.justificationMode =
                LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        attributes.recycle()
    }

    fun setMaxLines() {
        if (_maxLines != -1) {
            binding.textView.maxLines = _maxLines
        }
    }

    fun setEllipsize() {
        when (_ellipsize) {
            START -> binding.textView.ellipsize = TextUtils.TruncateAt.START
            MIDDLE -> binding.textView.ellipsize = TextUtils.TruncateAt.MIDDLE
            END -> binding.textView.ellipsize = TextUtils.TruncateAt.END
            MARQUEE -> binding.textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        }
    }

    fun setText(text: String?) {
        if (text != null)
            binding.textView.text = text
    }

    fun getText(): String = binding.textView.text.toString()


    fun setTextColor(color: Int) {
        binding.textView.setTextColor(ContextCompat.getColor(context, color))
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setSpan(
        colorResId: Int,
        clickablePart: String,
        isUnderlined: Boolean,
        isBoldText: Boolean,
        onClickListener: () -> Unit
    ) {
        binding.textView.withClickableSpan(
            colorResId, clickablePart, isUnderlined, isBoldText, onClickListener
        )
    }

    fun setColoredSpan(colorResId: Int, isBoldText: Boolean, subText: String) {
        binding.textView.setColoredSpan(colorResId, isBoldText, subText)
    }

    companion object {
        const val REGULAR = 0
        const val BOLD = 1
        const val MEDIUM = 2
        const val REGULAR_UNDERLINED = 3
        const val MEDIUM_UNDERLINED = 4
        const val BOLD_UNDERLINED = 5
        const val SEMIBOLD = 6
        const val SEMIBOLD_UNDERLINED = 7
        const val START = 1
        const val MIDDLE = 2
        const val END = 3
        const val MARQUEE = 4
        const val LEFT = 0
        const val RIGHT = 1
        const val JUSTIFY = 2
        const val CENTER = 3
    }
}
