function timeconvert(createDate) {
  const date = new Date(createDate);
  const current = new Date();
  let Showdate = "";
  if (date.toDateString() == current.toDateString()) {
    Showdate =
      "Today " +
      date
        .toLocaleTimeString()
        .replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
  } else {
    Showdate = date.toLocaleString();
  }
  return Showdate;
}
function location(a, b) {
  console.log("loaction" + a + b);
  for (let i = 0; i < b.length; i++) {
    console.log(i);
    if (b[i] == a) return i;
  }
}
export { timeconvert, location };
