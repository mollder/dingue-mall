export function loginByUserInfo(userData) {
  return axios({
    method: 'post',
    url: './login',
    data: {
      memberId: userData.id,
      memberPassword: userData.pw,
    }
  });
};
