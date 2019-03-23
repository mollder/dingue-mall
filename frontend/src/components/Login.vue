<template>
  <div>
    <b-form>
      <b-form-group>
        <b-form-input
          id="userIdInput"
          type="text"
          v-model="userId"
          required
          placeholder="아이디"
        />
      </b-form-group>
    </b-form>
  </div>
</template>

<script>
    export default {
        name: "Login",
    }
</script>

<style scoped>

</style>
