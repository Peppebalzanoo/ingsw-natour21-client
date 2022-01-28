package com.example.natour2.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2.R;
import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.controller.ControllerUser;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;
    private List<Itinerary> itineraryList;
    private TextView email;
    private TextView username;
    private CircleImageView profileImage;
    private Bitmap bitmapApp;
    private byte arrayBytesOfImageProfile[];
    private static final int REQUEST_GALLERY = 1000;
    private ImageView imageViewLogOut;
    private ImageView imageViewChangeProfileImage;


    private final ControllerHomeActivity ctrlHomeActivity = ControllerHomeActivity.getInstance();
    private final ControllerLoginSignin ctrlLogInSignIn = ControllerLoginSignin.getInstance();
    private final ControllerUser ctrlUser =  ControllerUser.getInstance();
    private final ControllerItinerary ctrlItinerary =  ControllerItinerary.getInstance();



    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctrlLogInSignIn.setActivity(requireActivity());
        ctrlLogInSignIn.setContext(requireActivity().getApplicationContext());
        ctrlLogInSignIn.setFragmentManager(requireActivity().getSupportFragmentManager());

        ctrlHomeActivity.setActivity(requireActivity());
        ctrlHomeActivity.setContext(requireActivity().getApplicationContext());
        ctrlHomeActivity.setFragmentManager(requireActivity().getSupportFragmentManager());

        ctrlUser.setActivity(getActivity());
        ctrlUser.setContext(getContext());
        ctrlItinerary.setActivity(getActivity());
        ctrlItinerary.setContext(getContext());
        /* ************************************************************************************** */
        //preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        /* ************************************************************************************** */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViewComponents(view);

        itineraryList = new ArrayList<>();
        itinerarioAdapter = new ItinerarioAdapter(getActivity(), getContext(), itineraryList, savedInstanceState, recyclerView);
        recyclerView.setAdapter(itinerarioAdapter);

        setListeners();

        readItinerari();

        return view;
    }


    public void initViewComponents(View view){
        email = view.findViewById(R.id.textViewNomeUtente);
        username = view.findViewById(R.id.textViewEmail);
        imageViewLogOut = view.findViewById(R.id.imageView_LogOut_ProfileFragment);
        imageViewChangeProfileImage = view.findViewById(R.id.imageView_ChangeImageProfile_ProfileFragment);
        profileImage = view.findViewById(R.id.imageViewProfile);
        recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        User u = ctrlUser.getActiveUser();
        email.setText(u.getEmail());
        username.setText(u.getUsername());
        showImage(u.getProfileImagePath(), profileImage);
    }


    public void setListeners(){
        imageViewChangeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //selectImageFromGallery();
                    selectProfileImage();
                }else {
                    requestPermission();
                }
            }
        });

        imageViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrlHomeActivity.showMainActivity(getContext());
            }
        });

    }

    private void readItinerari(){
        List<Itinerary> list = ctrlItinerary.getActiveUserItineraries();
        if(list == null){
            return;
        }
        itineraryList.addAll(list);
        itinerarioAdapter.notifyDataSetChanged();
    }

    public boolean checkPermission(){
        Boolean permission1 = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        Boolean permission2 = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        if(permission1 && permission2){
            return true;
        }
        return false;
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
    }

    public void selectImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImageFromGallery();
                }else{
                    Toast.makeText(getContext(), "Abilita il permesso 'READ_EXTERNAL_STORAGE'", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImageFromGallery();
                }else{
                    Toast.makeText(getContext(), "Abilita il permesso 'WRITE_EXTERNAL_STORAGE'", Toast.LENGTH_SHORT).show();
                }
                break;

            case 4:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    selectImageFromGallery();
                }else{
                    Toast.makeText(getContext(), "Abilita i permessi 'WRITE_EXTERNAL_STORAGE' e 'READ_EXTERNAL_STORAGE'", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 90) {
            try {
                Uri selectedImageUri = data.getData();

                System.out.println("************************************************************ prima");
                ctrlUser.updateProfileImage(selectedImageUri.toString());
                System.out.println("************************************************************ dopo");

                profileImage.setImageURI(selectedImageUri);
                bitmapApp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                arrayBytesOfImageProfile = convertBitmapToArrayOfByte(bitmapApp);
                createDirectoryAndSaveFile(convertByteToBitmap(arrayBytesOfImageProfile),  "NaTour21_IMG");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName){
        deleteImageIfAlredyExists();
        OutputStream outStream = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                ContentResolver resolver = getContext().getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName + ".jpeg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                outStream = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String directoryImg = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File image = new File(directoryImg, fileName + ".jpeg");
                outStream = new FileOutputStream(image);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            Objects.requireNonNull(outStream).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteImageIfAlredyExists() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/NaTour21_IMG.jpeg";
        new File(path).delete();
    }

    public Bitmap convertByteToBitmap(byte array[]){
        Bitmap bitmap = BitmapFactory.decodeByteArray(array , 0, array.length);
        return bitmap;
    }

    public byte[] convertBitmapToArrayOfByte(Bitmap bit){
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, byteOutStream);
        return byteOutStream.toByteArray();
    }

    private void selectProfileImage(){
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/jpeg");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, 90);
    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getActivity().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }



    private void showImage(String imagePath, ImageView imageProfile){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = null;
                    url = new URL(imagePath);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imageProfile.setImageBitmap(bmp);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /* ****************************************************************************************** */
//    private PreferanceManager preferanceManager;
//
//    private void showToast(String message){
//        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//    }


    // private void signOut(){
     //   showToast("Logout ...");
        /*FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferanceManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferanceManager.clear();
                    //DOVREMMO TORNARE AL FRAGMENT DI LOGIN()
                    //ctrl2.showLoginFragment();

                    //CODICE PER LE ACTIVITY
                    //startActivity(new Intent(getActivity().getApplicationContext(), LoginFragment));
                    //getActivity().finish();
                })
                .addOnFailureListener(e -> showToast("Unable to signout"));

         */
   // }
    /* ****************************************************************************************** */

}