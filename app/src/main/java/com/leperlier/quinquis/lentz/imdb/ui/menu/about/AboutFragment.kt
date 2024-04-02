package com.leperlier.quinquis.lentz.imdb.ui.menu.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gmail.eamosse.imdb.R

class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)

        val titleAbout: TextView = root.findViewById(R.id.title_about)
        val aboutText: TextView = root.findViewById(R.id.text_about)
        val explanationText = root.findViewById<TextView>(R.id.text_explanation)

        titleAbout.text = aboutViewModel.title
        aboutText.text = aboutViewModel.description
        explanationText.text = aboutViewModel.explanation

        val linkedinLink1: TextView = root.findViewById(R.id.linkedin_romain)
        val linkedinLink2: TextView = root.findViewById(R.id.linkedin_lucas)
        val linkedinLink3: TextView = root.findViewById(R.id.linkedin_yohan)

        setLink(linkedinLink1, "LinkedIn Romain", aboutViewModel.linkedInRomain)
        setLink(linkedinLink2, "LinkedIn Lucas", aboutViewModel.linkedInLucas)
        setLink(linkedinLink3, "LinkedIn Yohan", aboutViewModel.linkedInYohan)

        return root
    }

    private fun setLink(textView: TextView, text: String, url: String) {
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        spannableString.setSpan(clickableSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

}
