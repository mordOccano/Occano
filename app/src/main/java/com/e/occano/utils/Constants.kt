package com.e.occano.utils

class Constants{
    companion object{
        const val BASE_URL = "http://office.occano.io:4000/"

        //need to be done
        /*
       <form method = "POST" class="form-signin>{%csrf_token %}
       <h1 class="h3 mb-3 font-weight-normal">Reset password</h1>
       <input name ="email" class="form-control" placeholder = "Email address" type="email" id = "id_email" required="true">
       <button class="btn btn-lg btn-primary btn-block" type="submit">Send reset email</button>
       </form>

        <script type="text/javascript">

        var submitButton = document.getElementById('id_submit_btn');
        var form = document.getElementById('id_password_reset_form');

        //add listener to click Event
        submitButton.addEventListener('click', function(e){
        AndroidTextListener.onLoading(true)

        e.preventDefault();
        var email = document.getElementById("id_email").value

        var xhr = new XMLHttpRequest()'
        xhr.open('GET', '/api/account/check_if_exist/?email=' + email);
        xhr.onload = function(){
        if(xhr.status ===200){
        var response = JSON.parse(xhr.responseText)
        if response.response == email{
        console.log(email + "is a valid email")
        form.submit()
        AndroidTextListener.onSuccess(email)
        }
        else{
        console.log(email + " is Not valid email!")
        AndroidTextListener(email)
        }
        }
        else{
        console.log(xhr.status)
        }
        AndroidTextListener.onLoading(false)
        };
        xhr.send();
        });

        </script>
         */
        const val PASSWORD_RESET_URL: String = "http://office.occano.io:4000/password_reset/"

        const val NETWORK_TIMEOUT = 6000L
        const val CACHE_TIMEOUT = 2000L
        const val TESTING_NETWORK_DELAY = 0L // fake network delay for testing
        const val TESTING_CACHE_DELAY = 0L // fake cache delay for testing

        const val PAGINATION_PAGE_SIZE = 10

        const val GALLERY_REQUEST_CODE = 201
        const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 301
        const val CROP_IMAGE_INTENT_CODE: Int = 401

        var max_torque_gauge = 400f
        var max_bmep_gauge = 30f
        var max_break_power_gauge = 100000f
        var max_comp_pres_gauge = 300f
        var max_engine_speed_gauge = 100f
        var max_exhaust_gauge = 500f
        var max_firing_pres_gauge = 300f
        var max_fuel_gauge = 550f
        var max_imep_gauge = 30f
        var max_injec_gauge = 4f
        var max_load_gauge = 150f
        var max_scave_gauge = 10f


        var calib_torque_gauge = 400f
        var calib_bmep_gauge = 30f
        var calib_break_power_gauge = 100000f
        var calib_comp_pres_gauge = 300f
        var calib_engine_speed_gauge = 100f
        var calib_exhaust_gauge = 500f
        var calib_firing_pres_gauge = 300f
        var calib_fuel_gauge = 550f
        var calib_imep_gauge = 30f
        var calib_injec_gauge = 4f
        var calib_load_gauge = 150f
        var calib_scave_gauge = 10f

        var rpm_bool_calib: Boolean? = true
        var exshaust_temperature_bool_calib: Boolean? = true
        var load_bool_calib: Boolean? = true
        var firing_pressure_bool_calib: Boolean? = true
        var scavenging_pressure_bool_calib: Boolean? = true
        var compression_pressure_bool_calib: Boolean? = true
        var break_power_bool_calib: Boolean? = true
        var imep_bool_calib: Boolean? = true
        var torque_engine_bool_calib: Boolean? = true
        var bmep_bool_calib: Boolean? = true
        var injection_timing_bool_calib: Boolean? = true
        var fuel_flow_rate_bool_calib: Boolean? = true

        var local_mics_recv_current: String = ""
        var local_mics_recv_previous: String = ""
        var local_ir_recv_current: String = ""
        var local_ir_recv_tester_previous: String = ""

        var bool_mics_recv: Boolean = true
        var bool_ir_recv: Boolean = true

        var counter_compare_to_local:Int = 0
        var sec_bool_counter_compare_to_local:Boolean = false

    }
}