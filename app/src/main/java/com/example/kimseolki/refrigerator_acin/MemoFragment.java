package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Memo;
import com.example.kimseolki.refrigerator_acin.model.Memo_modify;
import com.example.kimseolki.refrigerator_acin.service.GetMemo;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-15.
 */

public class MemoFragment extends Fragment {

    private static final String TAG ="MemoFragment" ;
    private GetMemo getMemo;
    ListView listMemo;
    private ArrayList<Memo> MemoList;
    private ArrayList<Memo_modify> MemoModify;
    private MemoAdapter memo_adapter;
    ArrayList<Memo> memos;
    ArrayList<Integer> mld = new ArrayList<Integer>();//삭제 메모아이디 구분

    public void onStart() {
        super.onStart();
        loadMemo();
        registerForContextMenu(listMemo);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadMemo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_memo_main, container, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getMemo = retrofit.create(GetMemo.class);
        listMemo = (ListView) rootview.findViewById(R.id.lvMemo);
        loadMemo();

        Log.d(TAG,"실행됩니까1");
        listMemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getContext(),MemoModify.class);
                Bundle bundle = new Bundle();
                Integer memo_id = MemoModify.get(i).getId();
                String memo_author = MemoModify.get(i).getAuthor();
                String memo_memo = MemoModify.get(i).getMemo();

                bundle.putSerializable("memoid",(Serializable) memo_id);
                bundle.putSerializable("memoauthor",(Serializable)memo_author);
                bundle.putSerializable("memomemo",(Serializable)memo_memo);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootview.findViewById(R.id.mfab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(),MemoFragment_register.class);
                startActivity(intent1);
            }
        });

        return rootview;
    }

    private void loadMemo() {
        Call<ArrayList<Memo>> call = getMemo.all();
        call.enqueue(new Callback<ArrayList<Memo>>() {
            @Override
            public void onResponse(Call<ArrayList<Memo>> call, Response<ArrayList<Memo>> response) {
                memos = response.body();
                MemoList = new ArrayList<Memo>();
                MemoModify = new ArrayList<Memo_modify>(); //수정된거 담아

                for (int i = 0; i < memos.size(); i++) {
                    //Memo memo = new Memo(memos.get(i).getTitle(), memos.get(i).getAuthor(),memos.get(i).getMemo(), memos.get(i).getCreated_at(), memos.get(i).getModify_at());
                    Memo memo = new Memo(memos.get(i).getAuthor(), memos.get(i).getMemo());
                    MemoList.add(memo);
                    //수정
                    Memo_modify memo_modify = new Memo_modify(memos.get(i).getId(), memos.get(i).getAuthor(), memos.get(i).getMemo()); // 아이디랑 글쓴이 내용 담아
                    MemoModify.add(memo_modify);

                    mld.add(memos.get(i).getId()); //삭제에 필요한 메모 아이디 저장
                }
                displayResult(MemoList);
            }
            @Override
            public void onFailure(Call<ArrayList<Memo>> call, Throwable t) {

            }
        });
    }

    private void displayResult(ArrayList<Memo> memo) {
        if (memo != null) {
            //    MemoList.clear();
            memo_adapter = new MemoAdapter(getActivity(), memo);
            memo_adapter.notifyDataSetChanged();
            listMemo.setAdapter(memo_adapter);

        } else {

        }
    }

    //수정 삭제
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_lcilckmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.mDelete:
                contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                MemoList.remove(contextMenuInfo.position);
                memo_adapter.notifyDataSetChanged();
                Integer m = mld.get(contextMenuInfo.position);
                mld.remove(contextMenuInfo.position);
                removeMemo(m);
                return true;

            default:
                return true;
        }
    }

    private void removeMemo(Integer memo_id) {
        getMemo.removeMemo(memo_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}



