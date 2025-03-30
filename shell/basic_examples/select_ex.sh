!#/bin/bash


select COMPONENT in comp1 comp2 comp3 all none
do
case $COMPONENT in
comp1|comp2|comp3) echo "One component" ;;
all) echo "All components" ;;
none) break ;;
*) echo "ERROR: Invalid selection, $REPLY." ;;
esac
done
