package com.govahanpartner.com.model

data class VehicledataResponse(
    var `data`: List<VehicledataData>,
    var message: String,
    var status: Int
)

data class VehicledataData(
    var body_type: String,
    var id: Int,
    var no_tyres: Int,
    var vehicle_type: Int,
    var vehicle_name: String,
    var capacity: String,
    var vehicle_no: String,
    var year_model: String,
    var v_type: String,
    var no_of_tyers: String,
    var seat: String,
    var body_type_id: Int,

)