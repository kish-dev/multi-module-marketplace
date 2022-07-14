package com.software.core_utils.presentation.common

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun AppCompatImageView.setImageFromUrl(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}

private var defaultCoroutineExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    launchBody: suspend () -> Unit
): Job {
    return this.launch(defaultCoroutineExceptionHandler + context) {
        launchBody.invoke()
    }
}

fun View.setDebounceClickListener(
    delayInMillis: Long = 500,
    coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.Main.immediate
    ),
    onClick: () -> Unit
) {
    var job: Job? = null
    setOnClickListener {
        job?.cancel()
        job = coroutineScope.launch {
            onClick.invoke()
            delay(delayInMillis)
        }
    }
}
