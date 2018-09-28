package us.globalpay.manhattan.interactors;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import us.globalpay.manhattan.interactors.interfaces.IFirebasePointInteractor;
import us.globalpay.manhattan.models.geofire.PrizePointData;
import us.globalpay.manhattan.models.geofire.WildcardPointData;

/**
 * Created by Josué Chávez on 06/09/2018.
 */
public class FirebasePointInteractor implements IFirebasePointInteractor
{
    private static final String TAG = FirebasePointInteractor.class.getSimpleName();
    private Context mContext;
    private FirebasePointListener mListener;

    //Firebase
    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mGoldPoints = mRootReference.child("locationGoldYCR");
    private DatabaseReference mGoldPointsData = mRootReference.child("locationGoldYCRData");

    private DatabaseReference mSilverPoints = mRootReference.child("locationSilverYCR");
    private DatabaseReference mSilverPointsData = mRootReference.child("locationSilverYCRData");

    private DatabaseReference mBronzePoints = mRootReference.child("locationBronzeYCR");
    private DatabaseReference mBronzePointsData = mRootReference.child("locationBronzeYCRData");

    private DatabaseReference mWildcardPoints = mRootReference.child("locationWildcardYCR");
    private DatabaseReference mWildcardPointsData = mRootReference.child("locationWildcardYCRData");

    //Geofire References
    private GeoFire mReferenceGoldsPoint;
    private GeoFire mReferenceSilverPoint;
    private GeoFire mReferenceBronzePoint;
    private GeoFire mReferenceWildcardPoint;

    //GeoFire Queries
    private static GeoQuery mQueryGoldPoints;
    private static GeoQuery mQuerySilverPoints;
    private static GeoQuery mQueryBronzePoints;
    private static GeoQuery mQueryWildcardPoints;


    public FirebasePointInteractor(Context context, FirebasePointListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public void initializeGeolocation()
    {
        //new InitializeGeolocation().execute();
        mReferenceGoldsPoint = new GeoFire(mGoldPoints);
        mReferenceSilverPoint = new GeoFire(mSilverPoints);
        mReferenceBronzePoint = new GeoFire(mBronzePoints);
        mReferenceWildcardPoint = new GeoFire(mWildcardPoints);
    }

    @Override
    public void goldPointsQuery(GeoLocation location, double radius)
    {
        new GoldPointsQuery(mGoldPointsData, mReferenceGoldsPoint, location, radius, mListener).execute();
    }

    @Override
    public void goldPointsUpdateCriteria(GeoLocation location, double radius)
    {
        mQueryGoldPoints.setLocation(location, radius);
    }

    @Override
    public void silverPointsQuery(GeoLocation location, double radius)
    {
        new SilverPointsQuery(mSilverPointsData, mReferenceSilverPoint, location, radius, mListener).execute();
    }

    @Override
    public void silverPointsUpdateCriteria(GeoLocation location, double radius)
    {
        mQuerySilverPoints.setLocation(location, radius);
    }

    @Override
    public void bronzePointsQuery(GeoLocation location, double radius)
    {
        new BronzePointsQuery(mBronzePointsData, mReferenceBronzePoint, location, radius, mListener).execute();
    }

    @Override
    public void bronzePointsUpdateCriteria(GeoLocation location, double radius)
    {
        mQueryBronzePoints.setLocation(location, radius);
    }

    @Override
    public void wildcardPointsQuery(GeoLocation location, double radius)
    {
        new WildardPointsQuery(mWildcardPointsData, mReferenceWildcardPoint, location, radius, mListener).execute();
    }

    @Override
    public void wildcardPointsUpdateCriteria(GeoLocation location, double radius)
    {
        mQueryWildcardPoints.setLocation(location, radius);
    }

    @Override
    public void detachFirebaseListeners()
    {

    }



    /*
     ***************************************************************************************************************************
     *
     *
     *          ASYNCS
     *
     *
     ***************************************************************************************************************************
     */


    private class InitializeGeolocation extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            //GeoFire
            mReferenceGoldsPoint = new GeoFire(mGoldPoints);
            mReferenceSilverPoint = new GeoFire(mSilverPoints);
            mReferenceBronzePoint = new GeoFire(mBronzePoints);
            mReferenceWildcardPoint = new GeoFire(mWildcardPoints);
            return null;
        }
    }

    private static class GoldPointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;
        FirebasePointListener goldListener;

        DatabaseReference dbReferenceGold;
        //GeoQuery goldQuery;
        GeoFire goldReference;

        GoldPointsQuery(DatabaseReference firebaseReference, GeoFire reference, GeoLocation location, double radius, FirebasePointListener listener)
        {
            this.geoLocation = location;
            this.radius = radius;
            this.goldListener = listener;
            //this.goldQuery = query;
            this.goldReference = reference;
            this.dbReferenceGold = firebaseReference;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mQueryGoldPoints = goldReference.queryAtLocation(geoLocation, radius);
                mQueryGoldPoints.addGeoQueryEventListener(new GeoQueryEventListener()
                {
                    @Override
                    public void onKeyEntered(final String key, GeoLocation location)
                    {
                        dbReferenceGold.child(key).addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                PrizePointData goldPoint = dataSnapshot.getValue(PrizePointData.class);
                                goldListener.goldPoint_onDataChange(key, goldPoint);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                goldListener.goldPoint_onCancelled(databaseError);
                            }
                        });

                        LatLng geoLocation = new LatLng(location.latitude, location.longitude);
                        goldListener.query_goldPoint_onKeyEntered(key, geoLocation);
                    }

                    @Override
                    public void onKeyExited(String key)
                    {
                        goldListener.query_goldPoint_onKeyExited(key);
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location)
                    { }

                    @Override
                    public void onGeoQueryReady()
                    {
                        goldListener.query_goldPoint_onGeoQueryReady();
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error)
                    { }
                });
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private static class SilverPointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;
        FirebasePointListener silverListener;

        DatabaseReference dbReferenceSilver;
        //GeoQuery silverQuery;
        GeoFire silverReference;

        SilverPointsQuery(DatabaseReference firebaseReference, GeoFire reference, GeoLocation location, double radius, FirebasePointListener listener)
        {
            this.geoLocation = location;
            this.radius = radius;
            this.silverListener = listener;
            //this.silverQuery = query;
            this.silverReference = reference;
            this.dbReferenceSilver = firebaseReference;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mQuerySilverPoints = silverReference.queryAtLocation(geoLocation, radius);
                mQuerySilverPoints.addGeoQueryEventListener(new GeoQueryEventListener()
                {
                    @Override
                    public void onKeyEntered(final String key, GeoLocation location)
                    {
                        dbReferenceSilver.child(key).addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                PrizePointData silverPoint = dataSnapshot.getValue(PrizePointData.class);
                                silverListener.silverPoint_onDataChange(key, silverPoint);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                silverListener.silverPoint_onCancelled(databaseError);
                            }
                        });

                        LatLng geoLocation = new LatLng(location.latitude, location.longitude);
                        silverListener.query_silverPoint_onKeyEntered(key, geoLocation);
                    }

                    @Override
                    public void onKeyExited(String key)
                    {
                        silverListener.query_silverPoint_onKeyExited(key);
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location)
                    { }

                    @Override
                    public void onGeoQueryReady()
                    {
                        silverListener.query_silverPoint_onGeoQueryReady();
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error)
                    { }
                });
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private static class BronzePointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;
        FirebasePointListener bronzeListener;

        DatabaseReference dbReference;
        GeoFire bronzeReference;

        BronzePointsQuery(DatabaseReference firebaseReference, GeoFire reference, GeoLocation location, double radius, FirebasePointListener listener)
        {
            this.geoLocation = location;
            this.radius = radius;
            this.bronzeListener = listener;
            this.bronzeReference = reference;
            this.dbReference = firebaseReference;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mQueryBronzePoints = bronzeReference.queryAtLocation(geoLocation, radius);
                mQueryBronzePoints.addGeoQueryEventListener(new GeoQueryEventListener()
                {
                    @Override
                    public void onKeyEntered(final String key, GeoLocation location)
                    {
                        dbReference.child(key).addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                PrizePointData bronzeData = dataSnapshot.getValue(PrizePointData.class);
                                bronzeListener.bronzePoint_onDataChange(key, bronzeData);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                bronzeListener.bronzePoint_onCancelled(databaseError);
                            }
                        });

                        LatLng geoLocation = new LatLng(location.latitude, location.longitude);
                        bronzeListener.query_bronzePoint_onKeyEntered(key, geoLocation);
                    }

                    @Override
                    public void onKeyExited(String key)
                    {
                        bronzeListener.query_bronzePoint_onKeyExited(key);
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location)
                    { }

                    @Override
                    public void onGeoQueryReady()
                    {
                        bronzeListener.query_bronzePoint_onGeoQueryReady();
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error)
                    { }
                });
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private static class WildardPointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;
        FirebasePointListener wildcardListener;

        DatabaseReference dbReference;
        GeoFire wildcardReference;

        WildardPointsQuery(DatabaseReference firebaseReference, GeoFire reference, GeoLocation location, double radius, FirebasePointListener listener)
        {
            this.geoLocation = location;
            this.radius = radius;
            this.wildcardListener = listener;
            this.wildcardReference = reference;
            this.dbReference = firebaseReference;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mQueryWildcardPoints = wildcardReference.queryAtLocation(geoLocation, radius);
                mQueryWildcardPoints.addGeoQueryEventListener(new GeoQueryEventListener()
                {
                    @Override
                    public void onKeyEntered(final String key, GeoLocation location)
                    {
                        dbReference.child(key).addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                WildcardPointData wildcardData = dataSnapshot.getValue(WildcardPointData.class);
                                wildcardListener.wildcardPoint_onDataChange(key, wildcardData);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                wildcardListener.wildcardPoint_onCancelled(databaseError);
                            }
                        });

                        LatLng geoLocation = new LatLng(location.latitude, location.longitude);
                        wildcardListener.query_wildcardPoint_onKeyEntered(key, geoLocation);
                    }

                    @Override
                    public void onKeyExited(String key)
                    {
                        wildcardListener.query_wildcardPoint_onKeyExited(key);
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location)
                    { }

                    @Override
                    public void onGeoQueryReady()
                    {
                        wildcardListener.query_wildcardPoint_onGeoQueryReady();
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error)
                    { }
                });
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
