package statusbar.lyric.activity.page

import android.view.View
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.dialog.MIUIDialog
import statusbar.lyric.R
import statusbar.lyric.config.ActivityOwnSP
import statusbar.lyric.tools.ActivityTestTools.getClass
import statusbar.lyric.tools.ActivityTestTools.waitResponse
import statusbar.lyric.tools.Tools


@BMPage
class HookPage : BasePage() {
    override fun onCreate() {
        val testModeBinding = GetDataBinding({ ActivityOwnSP.config.testMode }) { view, _, data ->
            view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSSw(textId = R.string.TestMode, key = "testMode", onClickListener = { testModeBinding.send(it) })
        TextSSw(textId = R.string.RelaxConditions, tipsId = R.string.RelaxConditionsTips, key = "relaxConditions", dataBindingRecv = testModeBinding.getRecv(1))
        TextSA(textId = R.string.GetHook, onClickListener = {
            waitResponse()
            activity.getClass()
        }, dataBindingRecv = testModeBinding.getRecv(1))
        Line()
        TextSA(textId = R.string.ResetSystemUi, onClickListener = {
            MIUIDialog(activity) {
                setTitle(R.string.ResetSystemUi)
                setMessage(R.string.RestartUITips)
                setLButton(R.string.OK) {
                    Tools.shell("pkill -f com.android.systemui", true)
                    dismiss()
                }
                setRButton(R.string.Cancel) { dismiss() }
            }.show()
        })
    }
}