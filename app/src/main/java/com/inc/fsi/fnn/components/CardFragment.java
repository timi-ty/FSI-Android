package com.inc.fsi.fnn.components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inc.fsi.fnn.R;
import com.inc.fsi.fnn.utils.ImageHelper;

import org.w3c.dom.Text;

import java.security.SecureRandom;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String DATA_FOR_RANDOM_STRING = "0123456789";
    private static SecureRandom random = new SecureRandom();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        View fragView = inflater.inflate(R.layout.item_card, container);
        Bitmap backBitmap = ImageHelper.getRoundedCornerBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.card_back), 64);
        ConstraintLayout layout = fragView.findViewById(R.id.cardLayout);
        Drawable background = new BitmapDrawable(getResources(), backBitmap);
        layout.setBackground(background);

        TextView txtCardNumber = fragView.findViewById(R.id.txt_cardNumber);
        TextView txtCardAmount = fragView.findViewById(R.id.txt_cardBalance);
        String cardNumber = generateRandomString(16);
        StringBuilder sb = new StringBuilder(cardNumber);
        for(int i = 0 ; i < sb.length()-5; i++){
            if (sb.charAt(i) != ' '){
                sb.setCharAt(i,'*');
            }
        }
        txtCardNumber.setText(sb.toString());
        String amount = generateRandomString(4);
        StringBuilder am = new StringBuilder(amount);
        am.insert(1, ",").insert(0, "$");
        txtCardAmount.setText(am);

        return fragView;
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
            if ((i + 1) % 4 == 0 && i != length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
