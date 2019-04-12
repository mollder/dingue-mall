<template>
  <div>
    <b-navbar variant="faded" type="light">
      <b-navbar-nav>
        <b-nav-text>게시판····&nbsp;&nbsp;&nbsp;&nbsp;l&nbsp;&nbsp;&nbsp;&nbsp;내가 올리고 싶은 글을 자유롭게 올리는 곳</b-nav-text>

        <b-nav-item-dropdown text="목록 10개" right>
          <b-dropdown-item href="#">10개</b-dropdown-item>
          <b-dropdown-item href="#">20개</b-dropdown-item>
          <b-dropdown-item href="#">30개</b-dropdown-item>
          <b-dropdown-item href="#">40개</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-navbar>

    <b-table :items="items" :fields="fields" id="board-table"></b-table>

    <b-row>
      <b-col>
        <b-row>
          <b-col>
            <b-dropdown id="dropdown-1" text="제목+내용" class="m-md-2">
              <b-dropdown-item>제목+내용</b-dropdown-item>
              <b-dropdown-item>글제목</b-dropdown-item>
              <b-dropdown-item>글쓴이</b-dropdown-item>
              <b-dropdown-item>글번호</b-dropdown-item>
              <b-dropdown-item>댓글 내용</b-dropdown-item>
              <b-dropdown-item>댓글 작성자</b-dropdown-item>
            </b-dropdown>
          </b-col>

          <b-col>
            <b-form-input></b-form-input>
          </b-col>

          <b-col>
            <b-button variant="outline-primary">검색</b-button>
          </b-col>
        </b-row>
      </b-col>

      <b-col>
        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          aria-controls="board-table"
        ></b-pagination>
      </b-col>

      <b-col>
        <b-button variant="outline-primary">글쓰기</b-button>
      </b-col>
    </b-row>

  </div>
</template>

<script>
    export default {
      name: "Layout",
      data() {
          return {
            fields: ['writingNum', 'title', 'writer', 'date', 'view', 'up'],
            items: [
              { writingNum: 14317, title: '나 고딩때 남친 스님됐더라', writer: '우주소녀 보나', date: '19-04-10', view: 21706, up: 2 },
              { writingNum: 14316, title: '발로 짠 코드인데 버그가 안보인다.gif', writer: '제니', date: '19-04-10', view: 24708, up: 0 },
              { writingNum: 14315, title: '호그와트 시험기간 애브리타임', writer: '까륵', date: '19-04-10', view: 14312, up: 0 },
              { writingNum: 14314, title: '의정부고에 세워진 동상', writer: '선녀와난후끈', date: '19-04-10', view: 19460, up: 0 },
            ],
            currentPage: 1,
            perPage: 10,
          };
      },
      computed: {
        rows() {
          return this.items.length
        }
      }
    }
</script>

<style scoped>

</style>
