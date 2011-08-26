package dtd.phs.sms.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dtd.phs.sms.R;
import dtd.phs.sms.data.entities.SummariesList;
import dtd.phs.sms.global.ApplicationContext;
import dtd.phs.sms.util.Helpers;

public class SummariesAdapter extends BaseAdapter {



	private static final int ITEM_LAYOUT = R.layout.inbox_item;
	static final int STUB_AVATAR = R.drawable.icon;
	static Bitmap STUB_AVATAR_BITMAP = null;
	
	private SummariesList summaries;

	private ContactLoader contactsLoader;

	public SummariesAdapter(SummariesList summaries) {
		this.summaries = summaries;
		contactsLoader = new ContactLoader();
	}

	@Override
	public int getCount() {
		return summaries.size();
	}

	@Override
	public Object getItem(int position) {
		return summaries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class Holder {
		ImageView ivAvatar;
		public TextView tvContact;
		public TextView tvTime;
		public TextView tvShortDesc;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = getView(convertView);		
		Holder holder = (Holder) v.getTag();			
		updateHolder(position, holder);
		return v;
	}

	private View getView(View convertView) {
		View v = convertView;
		if ( v == null ) {
			v = Helpers.inflate(ITEM_LAYOUT,null);
			Holder holder = new Holder();
			holder.ivAvatar = (ImageView) v.findViewById(R.id.ivAvatar);
			holder.tvContact = (TextView) v.findViewById(R.id.tvContact);
			holder.tvTime = (TextView) v.findViewById(R.id.tvTime);
			holder.tvShortDesc = (TextView) v.findViewById(R.id.tvShortDesc);
			v.setTag(holder);
		}
		return v;
	}

	private void updateHolder(int position, Holder holder) {
		updateAvatar(position, holder);	
		updateContactName(position, holder);
		String contactId = summaries.getContactId(position);
		if ( contactId != null )
			contactsLoader.loadContact(holder.tvContact, holder.ivAvatar, contactId);

		holder.tvTime.setText(summaries.getLatestTime(position));
		holder.tvShortDesc.setText(summaries.getLatestActionMessage(position));
	}

	private void updateContactName(int position, Holder holder) {
		holder.tvContact.setText(summaries.getContactNumber(position) + "(" + summaries.getMessagesCount(position) + ")");
	}

	private void updateAvatar(int position, Holder holder) {
		if (STUB_AVATAR_BITMAP == null) {
			STUB_AVATAR_BITMAP = BitmapFactory.decodeResource(ApplicationContext.getInstance(null).getResources(), STUB_AVATAR);
		}
		holder.ivAvatar.setImageBitmap(STUB_AVATAR_BITMAP);
//		Uri uri = summaries.getAvatarURI(position);
//		if ( uri != null ) {
//			holder.ivAvatar.setImageURI(uri);
//		} else {
//			holder.ivAvatar.setImageResource(STUB_AVATAR);
//		}
	}
	
	public void stopLoadContacts() {
		contactsLoader.stopThread();		
		STUB_AVATAR_BITMAP = null;
	}

}
