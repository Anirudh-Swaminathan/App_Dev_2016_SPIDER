package com.anirudh.anirudhswami.spider_2016_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.anirudh.anirudhswami.spider_2016_3.model.MovieGrid;
import com.anirudh.anirudhswami.spider_2016_3.model.MovieRow;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link Moviesfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Seriesfrag extends Fragment {

    private static Context ct;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Moviesfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Seriesfrag newInstance(Context c) {
        Seriesfrag fragment = new Seriesfrag();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        ct = c;
        return fragment;
    }

    public Seriesfrag() {
        // Required empty public constructor
    }

    GridView aniGrid;
    DbHelper anidb = null;
    List<String> titles = new ArrayList<>();
    List<String> plots = new ArrayList<>();
    List<String> posters = new ArrayList<>();
    List<String> rats = new ArrayList<>();
    List<String> genres = new ArrayList<>();
    List<String> types = new ArrayList<>();

    String[] titleGrid;
    String[] plotGrid;
    String[] genreGrid;
    String[] ratGrid;
    String[] posterGrid;
    String[] typeGrid;

    boolean sorted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_seriesfrag, container, false);
        aniGrid = (GridView) rootView.findViewById(R.id.gridViewSeries);
        getMovies();

        setHasOptionsMenu(true);

        titleGrid = titles.toArray(new String[0]);
        plotGrid = plots.toArray(new String[0]);
        genreGrid = genres.toArray(new String[0]);
        ratGrid = rats.toArray(new String[0]);
        posterGrid = posters.toArray(new String[0]);
        typeGrid = types.toArray(new String[0]);

        aniGrid.setAdapter(new CustomGridAdapter(ct, typeGrid, titleGrid, ratGrid, posterGrid, plotGrid, genreGrid));
        aniGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ct, SingleMovie.class);

                CustomGridAdapter.ViewHolder holder = (CustomGridAdapter.ViewHolder) view.getTag();
                MovieGrid here = (MovieGrid) holder.moviImg.getTag();

                i.putExtra("title", here.getTitleG());
                i.putExtra("plot", here.getPlotG());
                i.putExtra("ge", here.getGenreG());
                i.putExtra("poster", here.getPosterG());
                i.putExtra("type", here.getTypeG());
                i.putExtra("rating", here.getRatingG());

                startActivity(i);
            }
        });

        return rootView;
    }

    public void getMovies() {
        try {
            final RuntimeExceptionDao<MovieRow, Integer> moviesDao = getHelper(ct).getMovieRunDao();
            List<MovieRow> movies = moviesDao.queryForEq("type", "series");
            int i = 0;
            while (i < movies.size()) {
                titles.add(movies.get(i).getTitle());
                plots.add(movies.get(i).getPlot());
                posters.add(movies.get(i).getImage());
                rats.add(movies.get(i).getRating());
                genres.add(movies.get(i).getGenre());
                types.add(movies.get(i).getType());
                i++;
            }
            if (anidb != null) {
                OpenHelperManager.releaseHelper();
                anidb = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DbHelper getHelper(Context ctx) {
        if (anidb == null) {
            anidb = OpenHelperManager.getHelper(ctx, DbHelper.class);
        }
        return anidb;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Sort){
            if(!sorted){
                getSortedSeries();
                Toast.makeText(ct, "Ordered series in descending order of IMDBRating", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void getSortedSeries(){
        try{
            QueryBuilder<MovieRow,Integer> qb = getHelper(ct).getMovieRunDao().queryBuilder();
            qb.where().eq("type","series");
            qb.orderBy("rating",false);
            List<MovieRow> movies = qb.query();
            int i=0;
            titles.clear();
            plots.clear();
            posters.clear();
            rats.clear();
            genres.clear();
            types.clear();
            while (i < movies.size()) {
                titles.add(movies.get(i).getTitle());
                plots.add(movies.get(i).getPlot());
                posters.add(movies.get(i).getImage());
                rats.add(movies.get(i).getRating());
                genres.add(movies.get(i).getGenre());
                types.add(movies.get(i).getType());
                i++;
            }

            titleGrid = titles.toArray(new String[0]);
            plotGrid = plots.toArray(new String[0]);
            genreGrid = genres.toArray(new String[0]);
            ratGrid = rats.toArray(new String[0]);
            posterGrid = posters.toArray(new String[0]);
            typeGrid = types.toArray(new String[0]);

            aniGrid.setAdapter(new CustomGridAdapter(ct, typeGrid, titleGrid, ratGrid, posterGrid, plotGrid, genreGrid));

            if (anidb != null) {
                OpenHelperManager.releaseHelper();
                anidb = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
