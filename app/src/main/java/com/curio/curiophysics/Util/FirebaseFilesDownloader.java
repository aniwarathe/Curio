package com.curio.curiophysics.Util;

import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by Chinthaka on 1/22/2018.
 */

public class FirebaseFilesDownloader {
    private String noteId;
    private int numberOfNotes;
    JSONObject jsonObject;
    final ArrayList<JSONObject> anims=new ArrayList<>();

    public FirebaseFilesDownloader(String noteId, int numberOfNotes) {
        this.noteId = noteId;
        this.numberOfNotes = numberOfNotes;
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://curiophysics.appspot.com").child("1.json");

    public JSONObject getFirebaseFiles(){



            /*try {
                final File firebaseFile=File.createTempFile("anim",".json");
                storageRef.getFile(firebaseFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.i("anim","success");
                        try {
                            InputStream inputStream=new FileInputStream(firebaseFile);
                            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                            StringBuilder responseStrBuilder = new StringBuilder();

                            String inputStr;
                            while ((inputStr = streamReader.readLine()) != null){
                                responseStrBuilder.append(inputStr);
                                //  Log.i("lottiefire",inputStr);
                            }
                            jsonObject = new JSONObject(responseStrBuilder.toString());


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("anim","failed");
                    }
                });
              //  anims.add(i,jsonObject);
                Log.i("size",String.valueOf(anims.size()));
            } catch (IOException e) {
                e.printStackTrace();
            }
           // Log.i("anim",String.valueOf(anims.size()));*/

        return jsonObject;

    }

    public JSONObject returnJson(){

        return jsonObject;
    }

}
