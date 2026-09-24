package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = null
  private var isTtsReady = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    tts = TextToSpeech(this, this)

    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          WhiskerPalWebView(
            ttsSpeakAction = { text ->
              speakOut(text)
            }
          )
        }
      }
    }
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val inLocale = Locale("en", "IN")
      val result = tts?.setLanguage(inLocale)
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        tts?.setLanguage(Locale.US)
      }
      tts?.setPitch(1.6f) // Squeaky Talking Tom pitch
      tts?.setSpeechRate(1.12f)
      isTtsReady = true
    }
  }

  private fun speakOut(text: String) {
    if (isTtsReady && tts != null) {
      // Strip emojis for clean TTS pronunciation
      val cleanText = text.replace(Regex("[\\p{So}\\p{Cn}]"), "").trim()
      if (cleanText.isNotEmpty()) {
        tts?.stop()
        tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, "WhiskerPalTTS")
      }
    }
  }

  override fun onDestroy() {
    tts?.stop()
    tts?.shutdown()
    tts = null
    super.onDestroy()
  }
}

class WebAppInterface(private val onSpeak: (String) -> Unit) {
  @JavascriptInterface
  fun speak(text: String) {
    onSpeak(text)
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WhiskerPalWebView(
  ttsSpeakAction: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val webAppInterface = remember { WebAppInterface(ttsSpeakAction) }

  AndroidView(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding(),
    factory = { ctx ->
      WebView(ctx).apply {
        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          allowFileAccess = true
          mediaPlaybackRequiresUserGesture = false
          cacheMode = WebSettings.LOAD_DEFAULT
        }
        webChromeClient = object : WebChromeClient() {
          override fun onPermissionRequest(request: android.webkit.PermissionRequest?) {
            request?.grant(request.resources)
          }
        }
        webViewClient = WebViewClient()
        addJavascriptInterface(webAppInterface, "AndroidTTS")
        loadUrl("file:///android_asset/index.html")
      }
    }
  )
}
