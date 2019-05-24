export const environment = {
  production: false,
  apiBaseUrl: 'localhost:8080/',
  apiInformation: 'api/information',
  apiRecommendation: 'api/recommendation',
  botRequestToken: 'ef7ac9da54b318ada90ed8c73eb29736',
  botBaseUrl: 'https://api.cai.tools.sap/build/v1/',
  botDialog: 'dialog',
  insuranceTypes: {
    getlivingstatus: 'Liability',
    getinsuredamount: 'Liability',
    getbiketype: 'Bike',
    getstudentstatus: 'Bike',
    getcartype: 'Car',
    getareatype: 'Car',
    getkilometers_per_year: 'Car'
  },
  dataFields: [
    'carType',
    'areaType',
    'kilometers_per_year',
    'liabilityType',
    'insured_amount',
    'biketype',
    'insurantType',
    'houseType',
    'age',
    'size',
    'living_space_in_square_meters'
  ]
};
