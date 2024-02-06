package com.louie.studybuddy.pages.login

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.louie.studybuddy.R

class RegisterPromptFrag: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.DialogTheme))

        assert(this.arguments != null)
        val emailAddress = this.arguments?.getString("email")

        builder.setTitle("Create new account?")
            .setMessage("$emailAddress is not registered. Create account instead?")
            .setPositiveButton("Yes", fun(_, _) {
                val registerFragment = RegisterFieldsFrag()
                val args = this@RegisterPromptFrag.arguments

                registerFragment.arguments = args
                registerFragment.show(requireActivity().supportFragmentManager, "Show Dialog")
            })
            .setNegativeButton("No", fun(_, _) {
                dismiss()
            })

        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_login_page, container, false)
    }
}