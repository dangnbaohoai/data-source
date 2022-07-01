import { getuser } from "../logic/api.js";

async function header() {
  const res = await getuser();
  return { avatar: res.avatar, username: res.userName };
}
//noti
//message
export { header };
