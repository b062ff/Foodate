package com.example.tindertest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tindertest.R;
import com.example.tindertest.model.CardModel;

public final class SimpleCardStackAdapter extends CardStackAdapter {

	private OnCardClickListener onCardClickListener;
	private OnCardDismissedListener onCardDismissedListener;

	public interface OnCardClickListener {
		void onCardClick(CardModel model);
	}
	public void setOnCardClickListener(OnCardClickListener listener) {
		this.onCardClickListener = listener;
	}
	public interface OnCardDismissedListener{
		void onLike(CardModel model);
		void onDislike(CardModel model);
	}
	public void setOnCardDismissedListener(OnCardDismissedListener listener){
		this.onCardDismissedListener = listener;
	}

	//
	public SimpleCardStackAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getCardView(int position, CardModel model, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.std_card_inner, parent, false);
			assert convertView != null;
		}
		//各要素(カード)にスワイプを設定
		//カードに要素をアップ
		ImageView imageView = convertView.findViewById(R.id.image);
		TextView titleTextView = convertView.findViewById(R.id.title);
		TextView descriptionTextView = convertView.findViewById(R.id.description);

		imageView.setImageDrawable(model.getCardImageDrawable());
		titleTextView.setText(model.getTitle());
		descriptionTextView.setText(model.getDescription());

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onCardClickListener != null) {
					onCardClickListener.onCardClick(model);
				}
			}
		});
		convertView.setOnTouchListener(new View.OnTouchListener() {
			private static final int MIN_SWIPE_DISTANCE = 10;

			private float downX, upX;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downX = event.getX();
						return true;
					case MotionEvent.ACTION_UP:
						upX = event.getX();
						float deltaX = upX - downX;

						if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE) {
							if (deltaX > 0 && model.getOnCardDismissedListener() != null) {
								onCardDismissedListener.onLike(model);
							} else if (deltaX < 0 && model.getOnCardDismissedListener() != null) {
								onCardDismissedListener.onDislike(model);
							}
						} else {
							if (onCardClickListener != null) {
								onCardClickListener.onCardClick(model);
							}
						}
						return true;
					default:
						return false;
				}
			}
		});


		return convertView;
	}
}
