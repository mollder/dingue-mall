<template>
  <div id="loginDiv">
    <h1> Ingue Mall</h1>
    <b-form id = "formGroup" action ="/auth" method="post">
      <b-form-group id ="inputGroup">
        <div id="idForm">
          <b-form-input
            id="username"
            name="username"
            class="userInput"
            type="text"
            v-model="userId"
            placeholder="아이디"
          />
        </div>

        <div id="passwordForm">
          <b-form-input
            id="password"
            name="password"
            class="userInput"
            type="password"
            v-model="userPassword"
            placeholder="비밀번호"
          />
        </div>
        <input type="submit" title="로그인" value="로그인" id="loginBtn"/>
      </b-form-group>
    </b-form>

  </div>
</template>

<script>
  import {loginByUserInfo} from '../api/auth';

    export default {
      name: "Login",

      data() {
          return {
            userId : '',
            userPassword : '',
          };
      },
      methods: {
        login() {
          console.log(this.userId);
          console.log(this.userPassword);
          loginByUserInfo({
            id : this.userId,
            pw : this.userPassword,
          }).then(response => {
            console.log(response);
            if (response.data === true) {
              this.$router.push('/success');
            }
          });
        },
      }
    }
</script>

<style scoped>

  h1 {
    font-size: 2.5em;
    color: #0000ff;
    width : 200px;
    margin: 110px auto 50px auto;
  }

  .userInput {
    height: 51px;
    margin: 0 0 14px;
  }

  #formGroup {
    width : 460px;
    margin: 0 auto;
    font-family: Helvetica,Dotum,sans-serif;
  }

  #loginBtn {
    display: block;
    width : 460px;
    font-family: Helvetica,Dotum,sans-serif;
    height: 61px;
    background-color: #0000ff;
    margin: 30px 0 14px;
    color: #fff;
    font-size : 20px;
    font-weight: 700;
    border: none;
    border-radius: 0;
    -webkit-appearance: none;
  }
</style>
