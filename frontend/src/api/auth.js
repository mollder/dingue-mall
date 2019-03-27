import axios from 'axios'

export function loginByUserInfo(userData) {
  return axios({
    method: 'post',
    url: './login',
    data: {
      userId : userData.id,
      userPassword: userData.pw,
    }
  });
};
