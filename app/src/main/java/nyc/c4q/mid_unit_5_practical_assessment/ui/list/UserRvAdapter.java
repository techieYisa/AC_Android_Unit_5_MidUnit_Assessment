package nyc.c4q.mid_unit_5_practical_assessment.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.R;
import nyc.c4q.mid_unit_5_practical_assessment.model.User;

/**
 * {@link RecyclerView.Adapter} that can display a
 * {@link nyc.c4q.mid_unit_5_practical_assessment.model.User} and makes a call to the
 * specified {@link UserListFragment.OnUserSelectedListener}.
 */
class UserRvAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private final List<User> users;
    private final UserListFragment.OnUserSelectedListener listener;

    UserRvAdapter(List<User> users, UserListFragment.OnUserSelectedListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_entry, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        final User user = users.get(position);

        Picasso.with(holder.thumbnailView.getContext())
                .load(user.getThumbnailImageUrl())
                .into(holder.thumbnailView);

        holder.nameView.setText(String.format("%s\n%s", user.getFirstName(), user.getLastName()));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onUserSelected(user.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // Useful method we can add to this class to facilitate updating the data for the RecyclerView
    void updateUsers(List<User> newUsers) {
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }
}
