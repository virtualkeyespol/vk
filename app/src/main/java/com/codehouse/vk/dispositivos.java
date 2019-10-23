package com.codehouse.vk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codehouse.vk.R.layout.fragment_dispositivos;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dispositivos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dispositivos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dispositivos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String token;

    private ListView listview;
    public ArrayList<String> dnombre = new ArrayList<>();
    public ArrayList<String> destado = new ArrayList<>();
    public ArrayList<String> dserie = new ArrayList<>();
    public ArrayList<String> did = new ArrayList<>();



    public ArrayAdapter<String> adapter ;
    private dispositivoItem d;
    private String url;

    //private ArrayList<String> names;

    private OnFragmentInteractionListener mListener;

    private void cargarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN","NO EXISTE");
        url = preferences.getString("URL","NONE");

    }


    public dispositivos() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dispositivos.
     */
    // TODO: Rename and change types and number of parameters
    public static dispositivos newInstance(String param1, String param2) {
        dispositivos fragment = new dispositivos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private void getDispositivos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService postService = retrofit.create(apiService.class);
        Call<datosDispositivosObjeto> call = postService.getdispositivoApi(url + "/rest/dispositivo/read?TOKEN=" + token);

        call.enqueue(new Callback<datosDispositivosObjeto>() {
            @Override
            public void onResponse(Call<datosDispositivosObjeto> call, Response<datosDispositivosObjeto> response) {
                System.out.println(response.toString());
                System.out.println(response.body().getRESPUESTA());
                if (response.body().getSTATUS().equals("OK")){
                    for(dispositivoObjecto post : response.body().getRESPUESTA()) {

                        dnombre.add(post.getNombre_dispositivo());
                        dserie.add(post.getModelo());
                        destado.add(post.getPropietario_nombre());
                        did.add(post.getDispositivo_id());
                    }
                } else {
                    System.out.println("Error de peticion");
                }


                dispositivoItem d = new dispositivoItem(getActivity(), dnombre.toArray(new String[0]), destado.toArray(new String[0]), dserie.toArray(new String[0]));
                listview.setAdapter(d);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<datosDispositivosObjeto> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cargarPreferencias();
        System.out.println(token);
        System.out.println(url);
        getDispositivos();
        View view = inflater.inflate(fragment_dispositivos, container, false);
        //dispositivoItem d = new dispositivoItem(getActivity(), titles.toArray(new String[0]));
        listview = (ListView) view.findViewById(R.id.listview);


        TextView vacio = view.findViewById(R.id.vaciod);
        vacio.setText("No existen dispositivos");
        listview.setEmptyView(vacio);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "Has pulsado: "+ dnombre.get(position), Toast.LENGTH_LONG).show();
                Intent b = new Intent(getActivity(),compartir.class);

                b.putExtra("dispositivo", dnombre.get(position));
                b.putExtra("id", did.get(position));
                startActivity(b);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
