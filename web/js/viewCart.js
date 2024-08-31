/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const host = "https://provinces.open-api.vn/api/";
var callAPI = (api) => {
    return axios.get(api)
            .then((response) => {
                renderData(response.data, "city");
            });
};
callAPI('https://provinces.open-api.vn/api/?depth=1');
var callApiDistrict = (api) => {
    return axios.get(api)
            .then((response) => {
                renderData(response.data.districts, "district");
            });
};
var callApiWard = (api) => {
    return axios.get(api)
            .then((response) => {
                renderData(response.data.wards, "ward");
            });
};

var renderData = (array, select) => {
    let row = '<option disable value="">Chọn ';
    if (select === 'city') {
        row += 'tỉnh / thành';
    } else if (select === 'district') {
        row += 'quận / huyện';
    } else if (select === 'ward') {
        row += 'phường / xã';
    }
    row += '</option>';

    array.forEach(element => {
        row += `<option data-id="${element.code}" value="${element.name}">${element.name}</option>`;
    });
    document.querySelector("#" + select).innerHTML = row;
};

$("#city").change(() => {
    callApiDistrict(host + "p/" + $("#city").find(':selected').data('id') + "?depth=2");
});
$("#district").change(() => {
    callApiWard(host + "d/" + $("#district").find(':selected').data('id') + "?depth=2");
});
$("#ward").change(() => {
});
