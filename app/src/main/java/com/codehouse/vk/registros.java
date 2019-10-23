package com.codehouse.vk;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codehouse.vk.R.layout.fragment_registros;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registros.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registros#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registros extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String token;
    private String url;
    private ListView listview;

    public ArrayList<String> dispositivo_id = new ArrayList<>();
    public ArrayList<String> nombre_dispositivo = new ArrayList<>();
    public ArrayList<String> modelo = new ArrayList<>();
    public ArrayList<String> propietario_nombre = new ArrayList<>();
    public ArrayList<String> propietario_username = new ArrayList<>();
    public ArrayList<String> rfecha = new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    public registros() {
        // Required empty public constructor
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN","NO EXISTE");
        url = preferences.getString("URL", "NO EXISTE");

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registros.
     */
    // TODO: Rename and change types and number of parameters
    public static registros newInstance(String param1, String param2) {
        registros fragment = new registros();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cargarPreferencias();
        getRegistros();
        View view = inflater.inflate(fragment_registros, container, false);

        listview = (ListView) view.findViewById(R.id.listviewr);
        TextView vacio = view.findViewById(R.id.vacior);
        vacio.setText("No existen registros");
        listview.setEmptyView(vacio);

        // Inflate the layout for this fragment
        return view;
    }

    private void getRegistros() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService postService = retrofit.create(apiService.class);
        Call<datosRegistrosObjeto> call = postService.getregistrosApi(url + "/rest/registro/read?TOKEN=" + token);

        call.enqueue(new Callback<datosRegistrosObjeto>() {
            @Override
            public void onResponse(Call<datosRegistrosObjeto> call, Response<datosRegistrosObjeto> response) {
                if (response.body().getSTATUS().equals("OK")){
                    for(registroObjeto post : response.body().getRESPUESTA()) {

                        nombre_dispositivo.add(post.getNombre_dispositivo());
                        modelo.add(post.getModelo());
                        propietario_nombre.add(post.getPropietario_nombre());
                        rfecha.add(post.getFecha());

                    }
                } else {
                    System.out.println("Error de peticion");
                }

                registroItem r = new registroItem(getActivity(),
                        nombre_dispositivo.toArray(new String[0]),
                        modelo.toArray(new String[0]),
                        propietario_nombre.toArray(new String[0]),
                        rfecha.toArray(new String[0]));


                listview.setAdapter(r);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<datosRegistrosObjeto> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

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
