package com.database.Collegemanegement_kiit;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class cameraactivity extends AppCompatActivity {
    Button submit,picture;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    ImageView img_view;
    database dbf;
    private int GALLERY = 1, CAMERA = 2;
    private int STORAGE_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraactivity);
        submit=(Button)findViewById(R.id.submit);
        picture=(Button)findViewById(R.id.images);
        img_view=(ImageView)findViewById(R.id.img_camera);
        dbf=new database(cameraactivity.this);
        File database1 = getApplicationContext().getDatabasePath(database.data);
        if(false == database1.exists()){
            dbf.getReadableDatabase();
            if(copyDatabase(this)){
                Toast.makeText(this, "Database copied successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            if(ContextCompat.checkSelfPermission(cameraactivity.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED &&
//                    ContextCompat.checkSelfPermission(cameraactivity.this,
//                    Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED )
//            {
//                Toast.makeText(cameraactivity.this, "permission already granted", Toast.LENGTH_SHORT).show();
//                showPictureDialog();
//            }
//            else
//                if(ContextCompat.checkSelfPermission(cameraactivity.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED )
//            {
//                AlertDialog.Builder as=new AlertDialog.Builder(cameraactivity.this);
//                    as.setTitle("Permission not granted");
//                    as.setMessage("This permission is needed to access storage");
//                    as.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    requestpermisson();
//                }
//            }).setCancelable(false).create().show();
//            }
//            else {
//                    AlertDialog.Builder ab=new AlertDialog.Builder(cameraactivity.this);
//                    ab.setTitle("Camera Permission not granted");
//                    ab.setMessage("This permission is needed to access camera");
//                    ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestpermisson();
//                        }
//                    }).setCancelable(false).create().show();
//                }
//        }
//});
               verifyPermissions();
            }});
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int id = Integer.parseInt(getIntent().getStringExtra("id1"));
                        Toast.makeText(cameraactivity.this, "the id is"+id, Toast.LENGTH_SHORT).show();
                        try {
                            long result = dbf.ImageInsert(id, imageViewToByte(img_view));
                            if (result > 0)
                                Toast.makeText(cameraactivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(cameraactivity.this,"Data Not Inserted",Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(cameraactivity.this,"Please Check code...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(cameraactivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    img_view.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(cameraactivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            img_view.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(cameraactivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(database.data);
            String outFileName = database.DBLOCATION + database.data;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("signup","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void verifyPermissions(){
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED){
            showPictureDialog();
        }else{
            ActivityCompat.requestPermissions(cameraactivity.this,
                    permissions,
                    STORAGE_PERMISSION_CODE);
        }
    }
//    private void requestpermisson() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(cameraactivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            ActivityCompat.requestPermissions(cameraactivity.this,
//                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
//        }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCod, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }
}