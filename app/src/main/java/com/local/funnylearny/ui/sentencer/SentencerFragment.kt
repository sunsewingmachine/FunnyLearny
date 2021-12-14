package com.local.funnylearny.ui.sentencer

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_sentencer.*
import kotlinx.android.synthetic.main.sentencer_list_item.*
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import android.R.attr.name
import android.speech.tts.UtteranceProgressListener


class SentencerFragment : Fragment(), TextToSpeech.OnInitListener {

    private var sentencerFragmentInteractionListener : SentencerFragmentInteractionListener? = null
    private var tts : TextToSpeech? = null
    private var wordTextView : TextView? = null
    private var meaningTextView : TextView? = null
    private var speakerImageView : ImageView? = null
    private lateinit var utterParam : HashMap<String, String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SentencerFragmentInteractionListener){
            sentencerFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("SentencerFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sentencer, container, false)
    }

    private val sentencer = ArrayList<Sentencer>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        sentencer.add(Sentencer("How?","எப்படி","something"))
        sentencer.add(Sentencer("is?","", "something"))
        sentencer.add(Sentencer("your?","உங்கள்","something"))
        sentencer.add(Sentencer("name?","பெயர்?","something"))
        sentencer.add(Sentencer("daddy","தந்தை","something"))
        sentencer.add(Sentencer("now?","இப்போது?","something"))
        adapterAttachment()
        speakerImageView = this.speakerImage
        wordTextView = this.wordText
        meaningTextView = this.meaningText
        tts = TextToSpeech(requireContext(),this)

        bottomSpeaker.setOnClickListener{
           speakOutAll()
        }
    }

    private fun adapterAttachment(){
        sentencerRecyclerView.layoutManager = LinearLayoutManager(context)
        sentencerRecyclerView.adapter = SentencerRecyclerViewAdapter(sentencer, onSpeakerImageClicked)
    }

    private fun initToolbar(){
        sentencerToolBar.setNavigationOnClickListener {
            sentencerFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    private var onSpeakerImageClicked = object : SentencerRecyclerViewAdapter.OnSpeakerImageClickListener{
        override fun onSpeakerImageClicked(sentencer: Sentencer) {
            speakOut(sentencer)
        }

    }


    override fun onInit(status : Int) {

        utterParam = HashMap<String, String>()
        utterParam[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "utterID"

        if (status == TextToSpeech.SUCCESS) {

            val trTR = Locale.Builder().setLanguage("ta").setRegion("IN").build()
            val result = tts!!.setLanguage(trTR
            )

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                speakerImage!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(sentencer: Sentencer){
        val text = sentencer.word + " " + sentencer.meaning
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,utterParam)
    }

    tts.setOnUtteranceProgressListener = object : UtteranceProgressListener(){
        override fun onStart(p0: String?) {
            TODO("Not yet implemented")
        }

        override fun onDone(p0: String?) {
            TODO("Not yet implemented")
        }

        override fun onError(p0: String?) {
            TODO("Not yet implemented")
        }

    }

    private fun speakOutAll(){
        val text = sentencer.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    interface SentencerFragmentInteractionListener : FragmentInteractionListener

    companion object {
        @JvmStatic
        fun newInstance() = SentencerFragment()
        const val TAG = "SentencerFragment"
    }
}