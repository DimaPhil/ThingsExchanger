package odeen.newrssreader.proj.view;

import android.support.v4.app.Fragment;

public class ChannelListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChannelListFragment();
    }
}
