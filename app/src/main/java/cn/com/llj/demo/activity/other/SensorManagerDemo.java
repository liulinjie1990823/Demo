package cn.com.llj.demo.activity.other;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/19.
 */
public class SensorManagerDemo extends DemoActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    Vibrator vibrator;
    @Bind(R.id.tv_sensor_size)
    TextView tv_sensor_size;
    @Bind(R.id.tv_sensor_content)
    TextView tv_sensor_content;

    @Override
    public int getLayoutId() {
        return R.layout.sensor_manager_demo;
    }

    @Override
    public void initViews() {
        tv_sensor_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        printSensorInfo();
    }

    private void printSensorInfo() {
        //从传感器管理器中获得全部的传感器列表
        List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        //显示有多少个传感器
        tv_sensor_size.setText("经检测该手机有" + allSensors.size() + "个传感器，他们分别是：\n");
        //显示每个传感器的具体信息
        for (Sensor s : allSensors) {
            String tempString = "  设备名称：" + s.getName() + " 设备版本：" + s.getVersion() + "  供应商：" + s.getVendor() + "\n";
            tv_sensor_content.append(tempString);
        }

    }

    @OnClick(R.id.btn_orientation)
    public void btn_orientation(View view) {
        switchSensorEvent(view.getId());
    }

    @OnClick(R.id.btn_accelerometer)
    public void btn_accelerometer(View view) {
        switchSensorEvent(view.getId());
    }

    @OnClick(R.id.btn_temperature)
    public void btn_temperature(View view) {
        switchSensorEvent(view.getId());
    }

    @OnClick(R.id.btn_magnetic_field)
    public void btn_magnetic_field(View view) {
        switchSensorEvent(view.getId());
    }

    @OnClick(R.id.btn_light)
    public void btn_light(View view) {
        switchSensorEvent(view.getId());
    }

    @OnClick(R.id.btn_unknown)
    public void btn_unknown(View view) {
        switchSensorEvent(view.getId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener2);
        }
    }

    private void switchSensorEvent(int id) {
        switch (id) {
            case R.id.btn_orientation:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                break;
            case R.id.btn_accelerometer:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                break;
            case R.id.btn_temperature:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                break;
            case R.id.btn_magnetic_field:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                break;
            case R.id.btn_light:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                break;
            case R.id.btn_unknown:
                break;
        }
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener2, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    private SensorEventListener2 sensorEventListener2 = new SensorEventListener2() {
        @Override
        public void onFlushCompleted(Sensor sensor) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    float[] values = event.values;
                    float x = values[0]; // x轴方向的重力加速度，向右为正
                    float y = values[1]; // y轴方向的重力加速度，向前为正
                    float z = values[2]; // z轴方向的重力加速度，向上为正
                    // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
                    int medumValue = 19;// 如果不敏感请自行调低该数值,低于10的话就不行了,因为z轴上的加速度本身就已经达到10了
                    if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                        Toast.makeText(mBaseFragmentActivity, "检测到摇晃，执行操作！", Toast.LENGTH_SHORT).show();
                        vibrator.vibrate(20);
                    }
                    break;


            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
