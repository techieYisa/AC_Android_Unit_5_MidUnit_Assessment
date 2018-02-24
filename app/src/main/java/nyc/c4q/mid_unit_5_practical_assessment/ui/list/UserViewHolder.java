package nyc.c4q.mid_unit_5_practical_assessment.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nyc.c4q.mid_unit_5_practical_assessment.R;

/**
 * ViewHolder for our User class
 * Created by charlie on 1/23/18.
 */
class UserViewHolder extends RecyclerView.ViewHolder {
    final ImageView thumbnailView;
    final TextView nameView;
    final ViewGroup container;

    UserViewHolder(View view) {
        super(view);
        thumbnailView = view.findViewById(R.id.user_list_entry_thumbnail);
        nameView = view.findViewById(R.id.user_list_entry_name);
        container = view.findViewById(R.id.user_list_entry_container);
    }
}
