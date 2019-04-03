import axios from 'axios'

export function loginByUserInfo(userData) {
  return axios({
    method: 'post',
    url: './auth',
    data: {
      userId : userData.id,
      userPassword: userData.pw,
    }
  });
};
