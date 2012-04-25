package hdcenter.vn.ui;

import hdcenter.vn.HDCenterActivity;
import hdcenter.vn.R;
import hdcenter.vn.SearchMovies;
import hdcenter.vn.data.IRequestListener;
import hdcenter.vn.utils.Helpers;
import hdcenter.vn.utils.StringHelpers;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

public class Topbar {

	private static final int FIRST_PAGE = 1;
	private Activity hostedActivity;
	private View rootView;
	private ImageView ivHome;
	private AutoCompleteTextView atSearch;
	private ImageView ivSearch;
	private Handler handler = new Handler();
	private IRequestListener searchListener;

	public Topbar(Activity activity, IRequestListener searchListener, View topBar) {
		this.hostedActivity = activity;
		this.rootView = topBar;
		this.searchListener = searchListener;
		setActions();
	}

	private void setActions() {
		createHomeButton();
		createSearchModule();
	}

	private void createHomeButton() {
		ivHome = (ImageView) rootView.findViewById(R.id.ivHome);
		ivHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(hostedActivity.getApplicationContext(),HDCenterActivity.class);
				Helpers.exitActivityFrom(hostedActivity,i);
			}
		});
	}
	
	private void createSearchModule() {
		atSearch = (AutoCompleteTextView) rootView.findViewById(R.id.atSearch);
	    ivSearch = (ImageView) rootView.findViewById(R.id.ivSearch);
	    ivSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickButtonSearch();
			}
		});
	}
	
	private void onClickButtonSearch() {
		String s = StringHelpers.replaceLowerSignCharacter(hostedActivity.getApplicationContext(), atSearch.getText().toString().trim());
		s = s.toLowerCase();
		if ( s.length() > 0 ) {
			Intent i = new Intent(hostedActivity.getApplicationContext(),SearchMovies.class);
			i.putExtra(SearchMovies.IEXTRA_KEYWORD, s);
			hostedActivity.startActivity(i);
		}
	}

}
