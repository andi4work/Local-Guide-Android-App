package com.maximoapps.in.localgudie_mapnavigation.adapters;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximoapps.in.localgudie_mapnavigation.helpers.Constants;
import com.maximoapps.in.localgudie_mapnavigation.helpers.GPSTracker;
import com.maximoapps.in.localgudie_mapnavigation.model.GooglePlacesResponseResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomGridAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<GooglePlacesResponseResults> results = new ArrayList<>();
    private List<GooglePlacesResponseResults> resultsDuplicate = new ArrayList<>();
    CustomFilter filter;
    String photo_ref;
    Float calculatedDistance;

    public CustomGridAdapter(Context c, List<GooglePlacesResponseResults> results) {
        mContext = c;
        this.results = results;
        this.resultsDuplicate = results;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return results.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            // grid = new View(mContext);
            grid = inflater.inflate(com.maximoapps.localgudie_mapnavigation.R.layout.item_grid, null);
        } else {
            grid = convertView;
        }
        TextView title = (TextView) grid.findViewById(com.maximoapps.localgudie_mapnavigation.R.id.tvGridItemTitle);
        ImageView photo = (ImageView) grid.findViewById(com.maximoapps.localgudie_mapnavigation.R.id.ivGridItemPhoto);
        TextView distance = (TextView) grid.findViewById(com.maximoapps.localgudie_mapnavigation.R.id.tvDistance);
        title.setText(results.get(position).getName());
        results.get(position).getGeometry();
        //distance.setText("2.2 Kms");

        //API LOCATION
        float distanceD;
        Location goolgeAPI = new Location("GoolgeAPILocation");
        goolgeAPI.setLatitude(results.get(position).getGeometry().getLocation().getLat());
        goolgeAPI.setLongitude(results.get(position).getGeometry().getLocation().getLng());

        //CURRENT LOCATION
        Location currentLocation = new Location("CurrentLocation");
        GPSTracker latlon = new GPSTracker(mContext);
        Double lat = latlon.getLatitude();
        Double lon = latlon.getLongitude();
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(lon);

        Log.e("CURRENTLL", lat + "\n" + lon);

        distanceD = goolgeAPI.distanceTo(currentLocation);
        int x = (int) distanceD;
        if (distanceD > 1000) {
            float distanceDKMS = distanceD / 1000;
            String s = String.valueOf(distanceDKMS).substring(0, 4);
            distance.setText(s + " Kms");
        } else {
            distance.setText(x + " Mts");

        }
        if (results.get(position).getPhotos() != null && results.get(position).getPhotos().size() > 0) {
            if (results.get(position).getPhotos().get(0) != null && !TextUtils.isEmpty(results.get(position).getPhotos().get(0).getPhotoReference())) {
                photo_ref = results.get(position).getPhotos().get(0).getPhotoReference();
                Picasso.with(mContext).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=" + Constants.API_KEY + "&photoreference=" + photo_ref).into(photo);

            }
        } else {
            photo.setImageResource(com.maximoapps.localgudie_mapnavigation.R.drawable.no_preview);
        }
        return grid;

    }


    public void refresh(List<GooglePlacesResponseResults> result) {
        results.addAll(result);
        notifyDataSetChanged();
    }

    public void refreshAll(List<GooglePlacesResponseResults> result) {
//        results.clear();
        results = result;
        notifyDataSetChanged();
    }

    public List<GooglePlacesResponseResults> getResults() {
        return results;
    }

    @Override
    public Filter getFilter() {

        if (filter == null) {

            filter = new CustomFilter();

        }
        return filter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence query) {
            FilterResults resultsFilter = new FilterResults();
            if (query != null && query.length() > 0) {
                query = query.toString().toUpperCase();
                ArrayList<GooglePlacesResponseResults> filters = new ArrayList<GooglePlacesResponseResults>();
                for (int i = 0; i < resultsDuplicate.size(); i++) {
                    if (resultsDuplicate.get(i).getName().toUpperCase().contains(query)) {
                        GooglePlacesResponseResults p = new GooglePlacesResponseResults(resultsDuplicate.get(i).getName(), resultsDuplicate.get(i).getPhotos(), resultsDuplicate.get(i).getGeometry(), resultsDuplicate.get(i).getPlace_id(), resultsDuplicate.get(i).getRating(), resultsDuplicate.get(i).getVicinity());
                        filters.add(p);
                    }
                }
                resultsFilter.count = filters.size();
                resultsFilter.values = filters;
            } else {
                resultsFilter.count = resultsDuplicate.size();
                resultsFilter.values = resultsDuplicate;
            }

            return resultsFilter;
        }

        @Override
        protected void publishResults(CharSequence query, FilterResults filterResults) {

            List<GooglePlacesResponseResults> result = (ArrayList<GooglePlacesResponseResults>) filterResults.values;

            refreshAll(result);
        }
    }
}