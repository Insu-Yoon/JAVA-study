```html
<form class="input-box">
      <div style="margin: 20px">
        <div class="small-boxes-100">
          <h3>contact us</h3>
          <p>Your Email</p>
          <input class="form-input" type="email" placeholder="email@example.com" />
        </div>
        <div class="small-boxes-50">
          <p>First name</p>
          <input class="form-input" type="text" />
        </div>
        <div class="small-boxes-50">
          <p>Last name</p>
          <input class="form-input" type="text" />
        </div>
        <div class="small-boxes-100">
          <p>Message</p>
          <textarea class="form-input" style="width: 100%; height: 100px"></textarea>
        </div>
        <div class="small-boxes-50">
            <input type="checkbox" id="sub"/> <label for="sub">Subscribe</label>
            <!-- label 테그로 글씨 적어주면, 해당 글씨 눌러도 check -->
        </div>
        <div class="small-boxes-50">
            <button type="submit" style= "margin-left: auto; margin-top: auto; display: block; padding: 5px;">SEND</button>
        </div>
      </div>
    </form>
```
```css
div,
input,
textarea {
  box-sizing: border-box;
}

body {
    margin: 0;
}

.input-box {
  margin: auto;
  width: 60%;
  max-width: 600px;
  padding: 30px;
  background-color: aquamarine;
  font-family: "gulim";
}
.small-boxes-100 {
  padding: 10px;
}

.small-boxes-50 {
  float: left;
  width: 50%;
  padding: 10px;
}

.form-input {
  margin: 0;
  border-radius: 4px;
  border: 1px solid #606060;
  width: 100%;
  height: 30px;
}
input[type="text"] {
  width: 100%;
}
input[type="checkbox"] {
  width: min-content;
  margin-top: 10px;
}

.input-box p {
  margin-bottom: 5px;
}
```
