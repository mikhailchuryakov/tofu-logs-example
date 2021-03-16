# tofu-logs-example

For these case classes 
```scala
case class UserHidden(name: String, @hidden surname: String)
case class UserMask(name: String, @masked(MaskMode.Erase) surname: String)
case class UserUnembed(name: String, @unembed surname: String)
case class UserUnembedWithMask(name: String, @unembed @masked(MaskMode.Erase) surname: String)
```
The following output
```
{...,"message":"hidden: UserHidden{name=name}","name":"name"}
{...,"message":"mask: UserMask{name=name,surname=...}","name":"name","surname":"surname"}
{...,"message":"unembed: UserUnembed{name=name,surname=surname}","name":"name"}
{...,"message":"unembed with mask: UserUnembedWithMask{name=name,surname=...}","name":"name"}
```
