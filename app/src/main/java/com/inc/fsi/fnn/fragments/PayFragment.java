package com.inc.fsi.fnn.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiDetector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.TextBlock;
import com.inc.fsi.fnn.R;
import com.inc.fsi.fnn.utils.BarcodeTrackerFactory;
import com.inc.fsi.fnn.utils.GraphicOverlay;

import java.io.IOException;

public class PayFragment extends Fragment {

    GraphicOverlay mGraphicOverlay;
    CameraSource mCameraSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_pay, container, false);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build() );
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

            }
        });

        mCameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .setRequestedFps(15.0f)
                .build();

        final SurfaceView cameraSurface = fragView.findViewById(R.id.cameraSurface);

        cameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
                    return;
                }
                try {
                    mCameraSource.release();
                    mCameraSource.start(cameraSurface.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(mCameraSource != null){
                    mCameraSource.stop();
                }
            }
        });


        return fragView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
