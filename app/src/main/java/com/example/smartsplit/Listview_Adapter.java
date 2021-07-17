package com.example.smartsplit;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartsplit.R;

import java.util.ArrayList;
import java.util.List;

class Listview_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList list;
    private int array[];
    private ArrayList<Integer> unequalValues;

    LayoutInflater mInflater;
    public Listview_Adapter(Context context,List list){
        this.context = context;
        this.list  = (ArrayList) list;
        array = new int[list.size()];
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return array[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
       // convertView=null;
        if (convertView == null) {
            unequalValues = new ArrayList<>();
            holder = new ViewHolder();
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_view_adapter, null);
            holder.caption = (EditText) convertView
                    .findViewById(R.id.editText);
            holder.caption.setTag(position);
            ((TextView)convertView.findViewById(R.id.unequal_list_text_view)).setText(list.get(position).toString());
//            holder.caption.setText(list.get(position).toString());
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        int tag_position=(Integer) holder.caption.getTag();
        holder.caption.setId(tag_position);
        holder.ref=position;
        holder.caption.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                final int position2 = holder.caption.getId();
//                final EditText Caption = (EditText) holder.caption;
//
//                if(Caption.getText().toString().length()>0){
//                   // list.set(position2,Integer.parseInt(Caption.getText().toString()));
//                    unequalValues.add(position2,Integer.parseInt(Caption.getText().toString()));
//                    Log.i("numbers",unequalValues+" in ontextchanged");
//
//                }else{
//                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final EditText Caption = (EditText) holder.caption;

                if(Caption.getText().toString().length()>0){
                array[holder.ref]=Integer.parseInt(holder.caption.getText().toString());
                 Log.i("numbers",holder.ref+"----"+array[holder.ref]+" in AfterTextChanged");
//                unequalValues.add(holder.ref,Integer.parseInt(holder.caption.getText().toString()));
//                Log.i("numbers",holder.ref+"----"+unequalValues+" in AfterTextChanged");
            }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return convertView;
    }

    public int[] getUnqualValues(){
        return array;
    }
}

class ViewHolder {
    EditText caption;
    int ref;
}