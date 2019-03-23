<template>
  <div id="loginDiv">
    <h1> Ingue Mall</h1>
    <b-form id = "formGroup">
      <b-form-group id ="inputGroup">
        <b-form-input
          id="userIdInput"
          class="userInput"
          type="text"
          v-model="userId"
          required
          placeholder="아이디"
        />

        <b-form-input
          id="userPasswordInput"
          class="userInput"
          type="password"
          v-model="userPassword"
          required
          placeholder="비밀번호"
        />

        <input type="submit" title="로그인" value="로그인" id="loginBtn"/>
      </b-form-group>
    </b-form>
  </div>
</template>

<script>
    export default {
      name: "Login",

      data() {
          return {
            userId : '',
            userPassword : '',
          };
      },
    }
</script>

<style scoped>

  h1 {
    font-size: 2.5em;
    color: #0000ff;
    width : 200px;
    margin: 50px auto 50px auto;
  }

  .userInput {
    height: 51px;
    margin: 0 0 14px;
  }

  #formGroup {
    width : 460px;
    margin: 0 auto;
  }

  #loginBtn {
    display: block;
    width: 100%;
    height: 61px;
    background-color: #0000ff;
    margin: 30px 0 14px;
    color: #fff;
    font-size : 20px;
  }
</style>
