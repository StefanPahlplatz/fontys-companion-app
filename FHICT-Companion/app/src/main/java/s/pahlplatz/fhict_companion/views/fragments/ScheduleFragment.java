package s.pahlplatz.fhict_companion.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import s.pahlplatz.fhict_companion.R;
import s.pahlplatz.fhict_companion.adapters.ScheduleAdapter;
import s.pahlplatz.fhict_companion.controllers.ScheduleController;
import s.pahlplatz.fhict_companion.utils.NetworkState;
import s.pahlplatz.fhict_companion.utils.WrapContentLinearLayoutManager;
import s.pahlplatz.fhict_companion.views.activities.AddBlockActivity;
import s.pahlplatz.fhict_companion.views.activities.RemoveBlockActivity;

/**
 * Fragment to show the user's schedule.
 */
public class ScheduleFragment extends Fragment implements ScheduleController.ScheduleListener {

    private ScheduleController controller;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView weekIndicator;
    private TextView dayIndicator;
    private TextView noData;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Set toolbar title.
        getActivity().setTitle("Schedule");

        // Assign UI elements.
        progressBar = (ProgressBar) view.findViewById(R.id.schedule_pbar);
        weekIndicator = (TextView) view.findViewById(R.id.schedule_textview_week);
        dayIndicator = (TextView) view.findViewById(R.id.schedule_textview_day);
        noData = (TextView) view.findViewById(R.id.schedule_no_data);

        setHasOptionsMenu(true);

        // Configure button actions.
        final Button prevWeek = (Button) view.findViewById(R.id.schedule_week_prev);
        prevWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                controller.prevWeek();
            }
        });

        final Button nextWeek = (Button) view.findViewById(R.id.schedule_week_next);
        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                controller.nextWeek();
            }
        });

        final Button prevDay = (Button) view.findViewById(R.id.schedule_day_prev);
        prevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                controller.prevDay();
            }
        });

        final Button nextDay = (Button) view.findViewById(R.id.schedule_day_next);
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                controller.nextDay();
            }
        });

        // Configure recyclerView.
        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        // Create schedule controller.
        controller = new ScheduleController(getActivity(), this);

        if (!NetworkState.isOnline()) {
            showSchedule();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.schedule, menu);
        menu.findItem(R.id.action_schedule_download).setVisible(NetworkState.isOnline());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_schedule_download:
                controller.save();
                return true;

            case R.id.action_schedule_today:
                controller.setToday();
                return true;

            case R.id.action_schedule_add:
                Intent addBlockActivity = new Intent(getActivity(), AddBlockActivity.class);
                startActivity(addBlockActivity);
                return true;

            case R.id.action_schedule_remove:
                Intent removeBlockActivity = new Intent(getActivity(), RemoveBlockActivity.class);
                startActivity(removeBlockActivity);
                return true;

            default:
                return false;
        }
    }

    /**
     * Displays the schedule of a certain day.
     * Called by the schedule object.
     */
    @Override
    public void showSchedule() {
        progressBar.setVisibility(View.INVISIBLE);

        ScheduleAdapter adapter = controller.getAdapter();
        if (adapter == null) {
            recyclerView.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Changes the selected item in the day spinner.
     * Called by the schedule object.
     *
     * @param day item to be shown.
     */
    @Override
    public void setDaySpinner(final String day) {
        dayIndicator.setText(day);
    }

    /**
     * Changes the selected item in the week spinner.
     * Called by the schedule object.
     *
     * @param week item to be selected.
     */
    @Override
    public void setWeekSpinner(final String week) {
        weekIndicator.setText(week);
    }

    /**
     * Controller couldn't find a schedule.
     */
    @Override
    public void noSchedule() {
        progressBar.setVisibility(View.GONE);
        noData.setVisibility(View.VISIBLE);
        noData.setText(R.string.no_schedule_found);
    }
}
