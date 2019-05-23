# java-num-2-vietnamese

## Purpose

We have a need to generate number in vietnamese text.

This seems to be a goverment compliance requirement to have final numbers on an invoice to be presented in both numeric and litteral text form

So this is a POC to convert numbers in Long format to String Vietnamese text in java

## Out of Scope

- Not supporting decimal.
Vietnamese currency is most common to be used in "thousands" unit.
E.g. 500 VND is the smallest paper bill, 1000 or 2000 VND is the most common bill still in circulation at the time of writting this.
It does not make a lot of sense to support decimal places since most of the calculation result will have left over numbers placed within the 1000 VND margin.
Its common in Vietnam to accept anything smaller than 1 VND to be nelegible.

## Testing

```shell
# Compile and run the main() method
>javac NumToViet.java && java NumToViet

# Or use provided Makefile
> make
javac NumToViet.java
java NumToViet
[Passed] result: |0| - |không|
Group size is1
[Passed] result: |-1| - |âm một|
Group size is1
[Passed] result: |1| - |một|
Group size is1
[Failed] result: |một| expected |một trăm|
Group size is2
[Failed] result: |một| expected |một nghìn|
Group size is1
[Failed] result: |một| expected |mười|
Some test(s) failed!
rm -f *.class
```

## Support

If you are using this, please leave a comment inside Github Issue to let me know.

Any Pull Request to improve this would be more than welcome.

## Project setup 

Project was written in 

- NeoVim
- Coc.nvim 
- Coc-Java 
- Eclipse Java LSP https://github.com/eclipse/eclipse.jdt.ls/

