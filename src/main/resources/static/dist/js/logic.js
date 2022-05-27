const items = document.querySelectorAll('.card');
const container = document.querySelector('.container');
const body = document.body;
const bgColorsBody = ["#46b886", "#ff96bd", "#9999fb", "#e8883c", "#34495d"];
let codes =
    [
      "信仰与日月同辉，亘古不灭。",
      "功成不必在我。－－胡适",
      "你我山前没相见，山后别相逢。",
      "买人们的争执，酿酒汤。",
      "太平湖底陈年水墨与黄金世界万物法则。"
    ];
let activeItem = container.querySelector(".active");
let isActive = true;

$.post({
  url: "/function/getValue",
  data: null,
  success: function (data) {
    console.log(data);
    codes = data.codes;
    routes = data.routes;

    code.style.setProperty("--fontsize", ((body.offsetWidth*0.5)/data.codes[0].length) - 1);
    code.style.setProperty("--fontwidth", ((body.offsetWidth*0.5)/data.codes[0].length));
    code.style.setProperty("--areawidth", body.offsetWidth*0.5);
    code.style.setProperty("--count", data.codes[0].length);
    code.textContent = data.codes[0];
  }
})

function setActive(item, index) {

  if (activeItem === item) return;

  if(activeItem) activeItem.classList.remove("active");

  // console.log(inputArea.offsetWidth/codes[index].length);

  let code = document.querySelector('.code');
  if(isActive) {
    code.classList.remove("active");
    isActive = false;
  }else {
    code.classList.add("active");
    isActive = true;
  }
  code.style.setProperty("--fontsize", ((body.offsetWidth*0.5)/codes[index].length) - 1);
  code.style.setProperty("--fontwidth", ((body.offsetWidth*0.5)/codes[index].length));
  code.style.setProperty("--areawidth", body.offsetWidth*0.5);
  code.style.setProperty("--count", codes[index].length);
  code.textContent = codes[index];

  item.classList.add("active");
  body.style.backgroundColor = bgColorsBody[index]
  activeItem = item;
}

items.forEach((item, index)=>{
  item.addEventListener('click', () => setActive(item, index))
})