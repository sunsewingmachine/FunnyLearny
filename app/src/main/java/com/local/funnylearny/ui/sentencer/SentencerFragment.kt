package com.local.funnylearny.ui.sentencer

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
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


class SentencerFragment : Fragment() {

    private var sentencerFragmentInteractionListener : SentencerFragmentInteractionListener? = null
    private var tts : TextToSpeech? = null
    private var wordTextView : TextView? = null
    private var meaningTextView : TextView? = null
    private var speakerImageView : ImageView? = null
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

    private val sentencerList = ArrayList<Sentencer>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        sentencerList.add(Sentencer("How?","எப்படி","something"))
        sentencerList.add(Sentencer("is?","", "something"))
        sentencerList.add(Sentencer("your?","உங்கள்","something"))
        sentencerList.add(Sentencer("name?","பெயர்?","something"))
        sentencerList.add(Sentencer("daddy","தந்தை","something"))
        sentencerList.add(Sentencer("now?","இப்போது?","something"))
        adapterAttachment()
        speakerImageView = this.speakerImage
        wordTextView = this.wordText
        meaningTextView = this.meaningText
        tts = TextToSpeech(requireContext(),{})

        bottomSpeaker.setOnClickListener{
           prepareDataAndSpeakOutAll(sentencerList)
        }
    }

    private fun adapterAttachment(){
        sentencerRecyclerView.layoutManager = LinearLayoutManager(context)
        sentencerRecyclerView.adapter = SentencerRecyclerViewAdapter(sentencerList, onSpeakerImageClicked)
    }

    private fun initToolbar(){
        sentencerToolBar.setNavigationOnClickListener {
            sentencerFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    private var onSpeakerImageClicked = object : SentencerRecyclerViewAdapter.OnSpeakerImageClickListener{
        override fun onSpeakerImageClicked(sentencer: Sentencer) {
            val arrayList = ArrayList<String>()
            arrayList.add(sentencer.word)
            arrayList.add(sentencer.meaning)
            prepareDataAndSpeakOutAll(arrayList)
        }

    }

    private fun prepareDataAndSpeakOutAll(arrayList : ArrayList<*>) {

        val allWordList = ArrayList<String>()

        val isTamilWord = when {
            arrayList[0] is String  -> {
                arrayList.forEach {
                    it as String
                    allWordList.add(it)
                }
                true
            }
            arrayList[0] is Sentencer -> {
                arrayList.forEach {
                    it as Sentencer
                    allWordList.add(it.word)
                }
                false
            }
            else -> false
        }
        speakOutAll(allWordList,isTamilWord)
    }

    private fun speakOutAll(allWordList: ArrayList<String>,isTamilWord: Boolean) {
        tts!!.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(p0: String?) {

            }
            override fun onDone(p0: String?) {
                allWordList.removeAt(0)
                speak(allWordList,isTamilWord)
            }

            override fun onError(p0: String?) {
            }

        })
        speak(allWordList,false)
    }

    private fun speak(allWordList : ArrayList<String>,isTamilWord : Boolean) {
        if(allWordList.isNotEmpty()) {
            val language = if(isTamilWord) {
                Locale.Builder().setLanguage("ta").setRegion("IN").build()
            } else {
                Locale.getDefault()
            }
            tts!!.language = language
            tts!!.speak(allWordList[0], TextToSpeech.QUEUE_FLUSH, null, "")
        }
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