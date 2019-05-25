export const environment = {
  production: false,
  apiBaseUrl: '',
  apiInformation: 'api/information',
  apiRecommendation: 'api/recommendation',
  apiStart: 'api/start',
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
    getkilometers_per_year: 'Car',
    getlivingspacesize: 'HomeContents',
    getllivingstatus: 'HomeContents',
    getage: 'Home',
    gethousetype: 'Home',
    gethome_insurance_amount: 'Home'
  },
  dataFields: [
    'cartype',
    'areatype',
    'kilometers_per_year',
    'liabilitytype',
    'insured_amount',
    'biketype',
    'insuranttype',
    'housetype',
    'age',
    'size',
    'living_space_in_square_meters',
    'money'
  ]
};
