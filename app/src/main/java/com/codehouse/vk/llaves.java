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
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codehouse.vk.R.layout.fragment_llaves;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link llaves.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link llaves#newInstance} factory method to
 * create an instance of this fragment.
 */
public class llaves extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String token;
    private String url;
    private ListView listview;

    public ArrayList<String> username = new ArrayList<>();
    public ArrayList<String> first_name = new ArrayList<>();
    public ArrayList<String> last_name = new ArrayList<>();
    public ArrayList<String> modelo = new ArrayList<>();
    public ArrayList<String> nombre = new ArrayList<>();
    public ArrayList<String> fecha_expiracion = new ArrayList<>();
    public ArrayList<String> codigo = new ArrayList<>();
    public ArrayList<String> mac = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public llaves() {
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
     * @return A new instance of fragment llaves.
     */
    // TODO: Rename and change types and number of parameters
    public static llaves newInstance(String param1, String param2) {
        llaves fragment = new llaves();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cargarPreferencias();
        getLlaves();
        View view = inflater.inflate(fragment_llaves, container, false);

        listview = (ListView) view.findViewById(R.id.listviewll);

        TextView vacio = view.findViewById(R.id.vacioll);
        vacio.setText("No existen llaves");
        listview.setEmptyView(vacio);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getActivity(), "Has pulsado: "+ codigo.get(position), Toast.LENGTH_LONG).show();
                Intent b = new Intent(getActivity(),conectar.class);
                b.putExtra("llave", codigo.get(position));
                b.putExtra("nombre_dispositivo",nombre.get(position));
                b.putExtra("mac", mac.get(position));

                startActivity(b);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void getLlaves() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService postService = retrofit.create(apiService.class);
        Call<datosLlavesObjeto> call = postService.getllavesApi(url + "/rest/llave/read?TOKEN=" + token);

        call.enqueue(new Callback<datosLlavesObjeto>() {
            @Override
            public void onResponse(Call<datosLlavesObjeto> call, Response<datosLlavesObjeto> response) {
                if (response.body().getSTATUS().equals("OK")){
                    for(llaveObjeto post : response.body().getRESPUESTA()) {

                        username.add(post.getUsername());
                        mac.add(post.getMac());
                        codigo.add(post.getCodigo());
                        first_name.add(post.getPropietario_nombre());
                        last_name.add(post.getLast_name());
                        modelo.add(post.getModelo());
                        fecha_expiracion.add(post.getFecha_expiracion());
                        nombre.add(post.getNombre_dispositivo());

                    }
                } else {
                    System.out.println("Error de peticion");
                }

                llaveItem ll = new llaveItem(getActivity(),
                        username.toArray(new String[0]),
                        first_name.toArray(new String[0]),
                        last_name.toArray(new String[0]),
                        modelo.toArray(new String[0]),
                        nombre.toArray(new String[0]),
                        fecha_expiracion.toArray(new String[0]),
                        codigo.toArray(new String[0]));


                listview.setAdapter(ll);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<datosLlavesObjeto> call, Throwable t) {
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
