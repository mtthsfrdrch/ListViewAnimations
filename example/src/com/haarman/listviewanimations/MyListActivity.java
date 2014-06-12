/*
 * Copyright 2013 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haarman.listviewanimations;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.widget.DynamicListView;

public class MyListActivity extends BaseActivity {

	private DynamicListView mListView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);
		mListView = (DynamicListView) findViewById(R.id.activity_mylist_listview);
		mListView.setDivider(null);
	}

	public DynamicListView getDynamicListView() {
		return mListView;
	}

	protected ArrayAdapter<Integer> createListAdapter() {
		return new MyListAdapter(this, getItems());
	}

	public static ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			items.add(i);
		}
		return items;
	}

	private static class MyListAdapter extends ArrayAdapter<Integer> {

		private final Context mContext;

		public MyListAdapter(final Context context, final ArrayList<Integer> items) {
			super(items);
			mContext = context;
		}

		@Override
		public long getItemId(final int position) {
			return getItem(position).hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view =  LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
			}
            ((TextView) view.findViewById(R.id.text)).setText("This is row number " + getItem(position));
			return view;
		}
	}
}
