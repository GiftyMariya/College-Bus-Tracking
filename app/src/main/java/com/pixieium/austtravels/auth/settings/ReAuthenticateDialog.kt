package com.pixieium.austtravels.auth.settings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pixieium.austtravels.auth.R


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.pixieium.austtravels.auth.databinding.DialogReAuthenticateBinding


class ReAuthenticateDialog : DialogFragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: DialogReAuthenticateBinding
    private var listener: FragmentListener? = null

    companion object {
        const val TAG = "ReAuthenticateDialog"

        fun newInstance(): ReAuthenticateDialog {
            return ReAuthenticateDialog()
        }
    }

    interface FragmentListener {
        fun onEnterPassword(password: String)
    }

    /**
     * The onCreateView() method is responsible for creating the Dialog Fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogReAuthenticateBinding.inflate(layoutInflater)
        mContext = mBinding.root.context

        mBinding.enterBtn.setOnClickListener {
            val password = mBinding.password.editText?.text.toString()
            listener?.onEnterPassword(password)
            dismiss()
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is FragmentListener) {
            context
        } else {
            throw RuntimeException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun getTheme() = R.style.RoundedCornersDialog
}