package br.guilhermetits.data

import android.content.Context
import android.nfc.NfcAdapter

public class NfcReader(context: Context) {

    lateinit var nfcAdapter: NfcAdapter

    init {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
    }

    fun startReading() {

    }

}