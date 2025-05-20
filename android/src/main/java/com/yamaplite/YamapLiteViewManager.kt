package com.yamaplite

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.YamapLiteViewManagerInterface
import com.facebook.react.viewmanagers.YamapLiteViewManagerDelegate

@ReactModule(name = YamapLiteViewManager.NAME)
class YamapLiteViewManager : SimpleViewManager<YamapLiteView>(),
  YamapLiteViewManagerInterface<YamapLiteView> {
  private val mDelegate: ViewManagerDelegate<YamapLiteView>

  init {
    mDelegate = YamapLiteViewManagerDelegate(this)
  }

  override fun getDelegate(): ViewManagerDelegate<YamapLiteView>? {
    return mDelegate
  }

  override fun getName(): String {
    return NAME
  }

  public override fun createViewInstance(context: ThemedReactContext): YamapLiteView {
    return YamapLiteView(context)
  }

  @ReactProp(name = "color")
  override fun setColor(view: YamapLiteView?, color: String?) {
    view?.setBackgroundColor(Color.parseColor(color))
  }

  companion object {
    const val NAME = "YamapLiteView"
  }
}
