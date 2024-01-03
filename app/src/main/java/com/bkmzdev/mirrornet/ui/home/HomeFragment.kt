package com.bkmzdev.mirrornet.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bkmzdev.mirrornet.databinding.FragmentHomeBinding
import com.wireguard.android.backend.Backend
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.android.backend.Tunnel.State
import com.wireguard.config.Config
import com.wireguard.config.InetEndpoint
import com.wireguard.config.InetNetwork
import com.wireguard.config.Interface
import com.wireguard.config.Peer


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root





        return root
        val switch: Switch = this.binding.switch3
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                println("Standard Switch is on")
            }else{
                println("Standard Switch is off")
            }
        }


        val tunnel: Tunnel = WgTunnel()
        val intentPrepare: Intent = GoBackend.VpnService.prepare(view?.context)
        val interfaceBuilder = Interface.Builder()
        val peerBuilder = Peer.Builder()
        val backend: Backend = GoBackend(view?.context)
        println("END OF INIT")
        Log.d("bkTag", "Init Complite")
        Log.d("bkTag", "Try To connect")
        println("TRY TO CONNECT")
        backend.setState(tunnel, State.UP, Config.Builder().setInterface(interfaceBuilder.addAddress(
            InetNetwork.parse("10.0.3.200/32")).parsePrivateKey("redacted").build())
            .addPeer(peerBuilder.addAllowedIp(InetNetwork.parse("0.0.0.0/0")).setEndpoint(
                InetEndpoint.parse("46.101.188.154:51820")).parsePublicKey("redacted").build())
            .build())





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class WgTunnel : Tunnel {
        override fun getName(): String {
            return "wgpreconf"
        }

        override fun onStateChange(newState: State) {

        }
    }
}